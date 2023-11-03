package thomasmccue.pa_c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This is the Product constructor, creates a Product object.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method used to set the Product ID.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method used to set the Product Name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method used to set the Product Price.
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method used to set the Product stock, i.e. how many are currently in inventory.
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method used to set the Minimum number of the Product.
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method used to set the Maximum number of the Product.
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method gets the Products ID.
     *
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method gets the Products Name.
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method gets the Products Price.
     *
     * @return price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * This method gets the number of Products in stock.
     *
     * @return stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * This method gets the minimum number of Products.
     *
     * @return min
     */
    public int getMin() {
        return this.min;
    }

    /**
     * This method gets the maximum number of Products.
     *
     * @return max
     */
    public int getMax() {
        return this.max;
    }

    /**
     * This method adds an associated part that is passed as an argument to the
     * associatedParts ObservableList.
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * This method deletes an associated part that is passed as an argument from the
     * associatedParts ObservableList.
     *
     * @param selectedAssociatedPart
     * @return true if delete was successful, false if delete fails
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean deleted = associatedParts.remove(selectedAssociatedPart);
        if (deleted) {
            return true;
        }
        return false;
    }

    /**
     * This method returns the ObservableList of all parts associated with this Product object.
     *
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
