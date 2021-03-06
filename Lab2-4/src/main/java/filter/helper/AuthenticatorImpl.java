package filter.helper;

import command.factory.CommandFactoryImpl;
import entity.User;

public class AuthenticatorImpl implements Authenticator {

    @Override
    public boolean hasAuthority(User user, String commandName) {
        switch (commandName) {
            // admin commands
            case CommandFactoryImpl.SAVE_PRICES_COMMAND:
            case CommandFactoryImpl.CHANGE_ROOM_STATUS_COMMAND:
            case CommandFactoryImpl.APPROVE_COMMAND:
            case CommandFactoryImpl.CHECK_IN_COMMAND:
            case CommandFactoryImpl.CHECK_OUT_COMMAND:
            case CommandFactoryImpl.SHOW_ROOMS_PAGE_COMMAND:
                return (user != null) && user.isAdmin();

            // user commands
            case CommandFactoryImpl.BOOK_COMMAND:
            case CommandFactoryImpl.PAY_COMMAND:
            case CommandFactoryImpl.CANCEL_RESERVATION_COMMAND:
            case CommandFactoryImpl.SIGN_OUT_COMMAND:
            case CommandFactoryImpl.SHOW_BOOK_PAGE_COMMAND:
            case CommandFactoryImpl.SHOW_RESERVATIONS_PAGE_COMMAND:
                return (user != null);

            // any credentials commands
            case CommandFactoryImpl.SHOW_HOME_PAGE_COMMAND:
            case CommandFactoryImpl.SHOW_LOGIN_PAGE_COMMAND:
            case CommandFactoryImpl.LOGIN_COMMAND:
            case CommandFactoryImpl.UPDATE_PAGE_COMMAND:
                return true;

            default:
                throw new IllegalArgumentException("Invalid command: " + commandName);
        }
    }

}
