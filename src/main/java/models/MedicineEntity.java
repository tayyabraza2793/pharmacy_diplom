package models;

import utils.ModelAnnotations.Order;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Medicine", schema = "dbo", catalog = "Pharmacy")
public class MedicineEntity {
    @Order(1)
    private int id;
    @Order(2)
    private int companyId;
    @Order(3)
    private String name;
    @Order(4)
    private String type;
    @Order(5)
    private String manDate;
    @Order(6)
    private String expDate;
    @Order(7)
    private int buyPrice;
    @Order(8)
    private int sellPrice;
    @Order(9)
    private int index;
    @Order(10)
    private int quantity;
    @Order(11)
    private int tabletsCount;
    @Order(12)
    private String dosage;
    @Order(13)
    private String description;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "Man_Date")
    public String getManDate() {
        return manDate;
    }

    public void setManDate(String manDate) {
        this.manDate = manDate;
    }

    @Basic
    @Column(name = "Exp_Date")
    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    @Basic
    @Column(name = "Buy_Price")
    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Basic
    @Column(name = "Sell_Price")
    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Basic
    @Column(name = "Index")
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
    @Column(name = "Tablets_Count")
    public int getTabletsCount() {
        return tabletsCount;
    }

    public void setTabletsCount(int tabletsCount) {
        this.tabletsCount = tabletsCount;
    }

    @Basic
    @Column(name = "Dosage")
    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Company_Id")
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicineEntity that = (MedicineEntity) o;

        if (id != that.id) return false;
        if (buyPrice != that.buyPrice) return false;
        if (sellPrice != that.sellPrice) return false;
        if (index != that.index) return false;
        if (quantity != that.quantity) return false;
        if (tabletsCount != that.tabletsCount) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (manDate != null ? !manDate.equals(that.manDate) : that.manDate != null) return false;
        if (expDate != null ? !expDate.equals(that.expDate) : that.expDate != null) return false;
        if (dosage != null ? !dosage.equals(that.dosage) : that.dosage != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (manDate != null ? manDate.hashCode() : 0);
        result = 31 * result + (expDate != null ? expDate.hashCode() : 0);
        result = 31 * result + buyPrice;
        result = 31 * result + sellPrice;
        result = 31 * result + index;
        result = 31 * result + quantity;
        result = 31 * result + tabletsCount;
        result = 31 * result + (dosage != null ? dosage.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
