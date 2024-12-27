package topsis.algorithm;

/*
 * copied from https://github.com/danigpam/topsis
 */
public class TopsisTest   {
    
	/*
     * Test case data used from video tutorial by Manoj Mathews
     * https://www.youtube.com/watch?v=Br1NQK0Iumg
     */
	public static void main(String[] args) {

		Criteria criteriaPrice = new Criteria("Price", 0.35, true);
		Criteria criteriaLocation = new Criteria("Location", 0.25);
		Criteria criteriaSize = new Criteria("Size", 0.4);

		Alternative house1 = new Alternative("House 1");
		house1.addCriteriaValue(criteriaPrice, 800000);
		house1.addCriteriaValue(criteriaLocation, 8);
		house1.addCriteriaValue(criteriaSize, 2000);
		
		Alternative house2 = new Alternative("House 2");
		house2.addCriteriaValue(criteriaPrice, 700000);
		house2.addCriteriaValue(criteriaLocation, 7);
		house2.addCriteriaValue(criteriaSize, 3000);
		
		Alternative house3 = new Alternative("House 3");
		house3.addCriteriaValue(criteriaPrice, 600000);
		house3.addCriteriaValue(criteriaLocation, 9);
		house3.addCriteriaValue(criteriaSize, 1000);
		
		Alternative house4 = new Alternative("House 4");
		house4.addCriteriaValue(criteriaPrice, 900000);
		house4.addCriteriaValue(criteriaLocation, 6);
		house4.addCriteriaValue(criteriaSize, 4000);
		
		Alternative house5 = new Alternative("House 5");
		house5.addCriteriaValue(criteriaPrice, 500000);
		house5.addCriteriaValue(criteriaLocation, 6);
		house5.addCriteriaValue(criteriaSize, 2000);
		
		Topsis topsis = new Topsis();
		topsis.addAlternative(house1);
		topsis.addAlternative(house2);
		topsis.addAlternative(house3);
		topsis.addAlternative(house4);
		topsis.addAlternative(house5);
		
		try {
			Alternative result = topsis.calculateOptimalSolution();
			System.out.println("The optimal solution is: " + result.getName());
			System.out.println("The optimal solution score is: " + result.getCalculatedPerformanceScore());
			
			printDetailedResults(topsis);
			
		} catch (TopsisIncompleteAlternativeDataException e) {
			System.err.println(e.getMessage());
		}	
    }
    
	private static void printDetailedResults(Topsis topsis) {
		
		System.out.println();
		System.out.println("Calculated closeness to ideal solution:");
		for (Alternative alternative : topsis.getAlternatives()) {
			System.out.println("Alternative: " + alternative.getName() + 
					" weight: " + alternative.getCalculatedPerformanceScore());
		}
	}
}