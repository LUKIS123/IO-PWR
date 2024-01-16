package pl.edu.pwr.application;

import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.ApplicationView;

public class ClientApplication implements ApplicationInterface {
    private final ApplicationView applicationView = new ApplicationView();
    private final User user;
    private final JobController jobController;

    public ClientApplication(User user, JobController jobController) {
        this.user = user;
        this.jobController = jobController;
    }

    @Override
    public void index() {
        int choice = -1;
        while (choice != 0) {
            choice = applicationView.clientMenu();
            switch (choice) {
                case 1:
                    createNewJob();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    listJobs();
                    break;
                default:
                    break;
            }
        }

    }

    void createNewJob() {
        int userId = user.getId();
        jobController.createNewOrder(userId);
    }

    void makePayment() {
        jobController.makePayment(user);
    }

    void listJobs() {
        int userId = user.getId();
        jobController.listJobsByOwner(userId);
    }

}
