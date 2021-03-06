package command.impl;

import command.Command;
import command.CommandResult;
import exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {

    private CommandResult result;

    public ShowPageCommand(String page) {
        result = CommandResult.createRedirectCommandResult(page);
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return result;
    }

}
