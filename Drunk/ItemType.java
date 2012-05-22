public class ItemType {

    private String id;
    private String product;
    private String qty;

	public ItemType() {
	}
	
	public ItemType(String id, String product, String qty) {
		this.id = id;
		this.product = product;
		this.qty = qty;
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQty() {
		
        return qty;
    }

    public void setQty(String qty) {
		this.qty = qty.split("\\.")[0];
    }

	public String toString() {
		return "ItemType: id: " + id + "/product: "+ product + "/qty: " + qty;
	}
}