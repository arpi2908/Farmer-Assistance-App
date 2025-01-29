package com.example.farmerassistantcustom.Model;

import java.io.Serializable;

public class Orders implements Serializable {
    String oid,pid,name,qauntity,amount,custname,dt,FarmerName,uid,status,fid,block,previousblock;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQauntity() {
        return qauntity;
    }

    public void setQauntity(String qauntity) {
        this.qauntity = qauntity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getFarmerName() {
        return FarmerName;
    }

    public void setFarmerName(String farmerName) {
        FarmerName = farmerName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getPreviousblock() {
        return previousblock;
    }

    public void setPreviousblock(String previousblock) {
        this.previousblock = previousblock;
    }
}
