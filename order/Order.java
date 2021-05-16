package hello.core.order;
// 주문이 다 끝났을때 처리되는 객체
public class Order {
    private Long memberID;
    private String itemName;
    private int itemPrice;
    private int discPrice;

    public Order(Long memberID, String itemName, int itemPrice, int discPrice) {
        this.memberID = memberID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discPrice = discPrice;
    }
    public int calculatePrice(){
        return itemPrice - discPrice ;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getDiscPrice() {
        return discPrice;
    }

    public void setDiscPrice(int discPrice) {
        this.discPrice = discPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "memberID=" + memberID +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discPrice=" + discPrice +
                ", calculatePrice=" + calculatePrice()+
                '}';
    }
}
