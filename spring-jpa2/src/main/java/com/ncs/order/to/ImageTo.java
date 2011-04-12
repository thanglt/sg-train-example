package com.ncs.order.to;

import javax.persistence.*;

@Entity
@NamedQueries(
        {
//                @NamedQuery(name="getById" , query = "select image from ImageTo image where image.imageId = :imageId"),
//                @NamedQuery(name="getAll" , query = "select image from ImageTo image")
        }
)
@Table(name="tbl_ord_image")
public class ImageTo extends BaseEntity{

	// Fields    
//     private OrderTO order;
     private String path;
     private String seq;

    public ImageTo() {
    }

    @Column(length=150)
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(length = 10)
    public String getSeq() {
        return this.seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}