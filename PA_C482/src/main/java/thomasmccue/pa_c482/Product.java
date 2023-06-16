package thomasmccue.pa_c482;

import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setMin(int min){
        this.min = min;
    }
    public void setMax(int max){
        this.max = max;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public int getStock(){
        return this.stock;
    }
    public int getMin(){
        return this.min;
    }
    public int getMax(){
        return this.max;
    }
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /* "a logical or runtime error that you corrected in the code and how it was corrected"
    ** One runtime error that I corrected here was that when deleteAssociatedPart was called it would cause a runtime error if the
    ** associated parts list was empty. I fixed it by adding a check for that in my if/else statement, and throwing an exception in that case.
    */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.size() > 0){
            boolean deleted = associatedParts.remove(selectedAssociatedPart);
            if(deleted) {
                return true;
            }else{
                throw new RuntimeException("The selected part could not be deleted");
            }
        } else{
          throw new RuntimeException("There are no associated parts");
        }
    }
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
