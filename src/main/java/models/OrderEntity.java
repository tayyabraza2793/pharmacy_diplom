package models;

import utils.ModelAnnotations.Order;

import javax.persistence.*;

@Entity
@Table(name = "Order", schema = "dbo", catalog = "Pharmacy")
public class OrderEntity {
    @Order(1)
    private int id;
    @Order(2)
    private int userId;
    @Order(3)
    private int medicineId;
    @Order(4)
    private int quantity;
    @Order(5)
    private int sum;
    @Order(6)
    private String status;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "Sum")
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Basic
    @Column(name = "User_Id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "Medicine_Id")
    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    @Basic
    @Column(name = "Status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (quantity != that.quantity) return false;
        if (sum != that.sum) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + quantity;
        result = 31 * result + sum;
        return result;
    }
}
