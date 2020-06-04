package com.example.notebaitap;

public class NoiDung {
    private int IdND;
    private String TenTD;


    public int getIdND() {
        return IdND;
    }

    public void setIdND(int idND) {
        IdND = idND;
    }

    public String getTenTD() {
        return TenTD;
    }

    public void setTenTD(String tenTD) {
        TenTD = tenTD;
    }

    public NoiDung(int idND, String tenTD) {
        IdND = idND;
        TenTD = tenTD;
//        CurrentDate = currentDate;

    }
}
