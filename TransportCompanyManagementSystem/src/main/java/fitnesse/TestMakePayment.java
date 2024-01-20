package fitnesse;

import fit.ColumnFixture;

public class TestMakePayment extends ColumnFixture {

    int calculatedCost;
    int expectedCost;

    public boolean calculateJobCost() {


        System.out.println(Setup.jobRepository.getById(1).getCost());
        return Setup.jobRepository.getById(1).getCost() == expectedCost;

//        Class<?> JobController = JobController.class;
//        try {
//            System.out.println(SetUp.callParseMethod(JobController,"calculateJobCost"));
//
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

    }

}
