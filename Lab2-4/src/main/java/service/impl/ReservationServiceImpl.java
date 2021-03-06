package service.impl;

import builder.Builder;
import dao.DaoHelper;
import dao.api.ReservationDao;
import entity.User;
import entity.reservation.Reservation;
import entity.reservation.ReservationStatus;
import entity.room.Room;
import entity.room.RoomClass;
import exception.DaoException;
import exception.ServiceException;
import service.api.ReservationService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private ReservationDao dao;

    public ReservationServiceImpl(DaoHelper daoHelper, Builder<Reservation> builder) {
        dao = daoHelper.reservationDao(builder);
    }

    @Override
    public List<Reservation> getAllReservations() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Reservation> getById(int id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Reservation> getByUserId(int userId) throws ServiceException {
        try {
            return dao.getByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void book(User user, Date arrivalDate, Date departureDate, RoomClass roomClass, int personsAmount)
            throws ServiceException {
        Reservation reservation = new Reservation(user,
                roomClass, null,
                ReservationStatus.WAITING,
                arrivalDate, departureDate,
                personsAmount, null);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public void approve(int id, Room room, BigDecimal totalPrice) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.WAITING) {
            throw new ServiceException("Can't approve reservation which is " + reservationStatus);
        }
        reservation.setRoom(room);
        reservation.setTotalPrice(totalPrice);
        reservation.setReservationStatus(ReservationStatus.APPROVED);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setPaid(int id) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.APPROVED) {
            throw new ServiceException("Can't set paid reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(ReservationStatus.PAID);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCheckedIn(int id) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.PAID) {
            throw new ServiceException("Can't set checked in reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(ReservationStatus.CHECKED_IN);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCheckedOut(int id) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.CHECKED_IN) {
            throw new ServiceException("Can't set checked out reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(ReservationStatus.CHECKED_OUT);
        try {
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void cancel(int id) throws ServiceException {
        try {
            Optional<Reservation> optional = dao.getById(id);
            if (!optional.isPresent()) {
                throw new ServiceException("Reservation not found by id: " + id);
            }
            Reservation reservation = optional.get();
            ReservationStatus reservationStatus = reservation.getReservationStatus();
            if (reservationStatus == ReservationStatus.CANCELLED || reservationStatus == ReservationStatus.CHECKED_OUT) {
                throw new ServiceException("Can't cancel reservation which is " + reservationStatus);
            }
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            dao.save(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Reservation getByIdOrElseThrowException(int id) throws ServiceException {
        try {
            return dao.getById(id)
                    .orElseThrow(() -> new ServiceException("Reservation not found by id: " + id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
