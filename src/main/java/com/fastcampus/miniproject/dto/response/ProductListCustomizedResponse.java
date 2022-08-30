package com.fastcampus.miniproject.dto.response;

import com.fastcampus.miniproject.dto.ProductCustomizedDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductListCustomizedResponse {

    private List<ProductCustomizedDto> loan;
    private List<ProductCustomizedDto> fund;
    private List<ProductCustomizedDto> saving;

    public ProductListCustomizedResponse(List<ProductCustomizedDto> loan, List<ProductCustomizedDto> fund, List<ProductCustomizedDto> saving) {
        this.loan = loan;
        this.fund = fund;
        this.saving = saving;
    }
}