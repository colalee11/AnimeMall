package com.dlsd.property.models.response;

import com.dlsd.property.models.DoctorSearch;

public class DoctorSearchResp extends BaseResp {
    private DoctorSearch data;

    public DoctorSearch getData() {
        return data;
    }

    public void setData(DoctorSearch data) {
        this.data = data;
    }
}
