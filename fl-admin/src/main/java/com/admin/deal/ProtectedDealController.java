package com.admin.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.response.ProtectedDealAdminResponse;
import com.common.home.mapper.HomeMapper;
import com.common.user.mapper.UserMapper;
import com.common.utils.SuccessResponse;
import com.service.deal.ProtectedDealAdminService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/api/admin/")
public class ProtectedDealController {
    /**
     * 모든 신청 거래된 안전거래 조회
     */
    private final ProtectedDealAdminService protectedDealAdminService;
    private final UserMapper userMapper;
    private final ProtectedDealMapper protectedDealMapper;
    private final HomeMapper homeMapper;

    @GetMapping
    public ResponseEntity<?> findAllBeforeDeposit(){
        List<ProtectedDealAdminResponse> allBeforeDeposit = protectedDealAdminService.findAllBeforeDeposit();
        SuccessResponse response = new SuccessResponse(true, "입금 신청 안전거래 조회 성공",allBeforeDeposit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
