public class DrunkTest {
	
	public static void main (String[] args) {
			ItemType i1 = new ItemType("111", "111", "111");
			ItemType i2 = new ItemType("222", "222", "222");
			ItemType[] itArray = new ItemType[2];
			itArray[0] = i1;
			itArray[1] = i2;

			System.out.println("printing return values-----");
			System.out.println(itArray[0].getProduct() + "-----" + itArray[1].getProduct());
		
	}
}