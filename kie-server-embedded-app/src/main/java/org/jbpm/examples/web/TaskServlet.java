package org.jbpm.examples.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.examples.util.ServiceUtils;
import org.kie.api.task.model.TaskSummary;

public class TaskServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String cmd = req.getParameter("cmd");
        String user = req.getParameter("user");

        if (cmd.equals("list")) {

            List<TaskSummary> taskList;
            try {
                taskList = ServiceUtils.getRuntimeDataService().getTasksAssignedAsPotentialOwner(user, null);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            req.setAttribute("taskList", taskList);
            ServletContext context = this.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/task.jsp");
            dispatcher.forward(req, res);

        } else if (cmd.equals("approve")) {

            String message = "";
            long taskId = Long.parseLong(req.getParameter("taskId"));
            try {
                ServiceUtils.getUserTaskService().start(taskId, user);
                ServiceUtils.getUserTaskService().complete(taskId, user, null);
                message = "Task (id = " + taskId + ") has been completed by " + user;
            } catch (Exception e) {
                throw new ServletException(e);
            }
            req.setAttribute("message", message);
            ServletContext context = this.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, res);

        }
    }
}