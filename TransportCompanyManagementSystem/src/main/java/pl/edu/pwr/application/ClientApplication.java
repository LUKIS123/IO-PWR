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
                    jobController.createNewOrder(user.getId());
                    break;
                case 2:
                    jobController.makePayment(user);
                    break;
                case 3:
                    jobController.listJobsByOwner(user.getId());
                    break;
                default:
                    break;
            }
        }

    }

}
