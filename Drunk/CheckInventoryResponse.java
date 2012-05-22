public class CheckInventoryResponse {
	private ItemType[] purchaseItems;
    private boolean toPurchase;

    public CheckInventoryResponse(ItemType[] purchaseItems, boolean toPurchase) {
        this.purchaseItems = purchaseItems;
        this.toPurchase = toPurchase;
    }

    public ItemType[] getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(ItemType[] purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    public boolean isToPurchase() {
        return toPurchase;
    }

    public void setToPurchase(boolean toPurchase) {
        this.toPurchase = toPurchase;
    }
}
