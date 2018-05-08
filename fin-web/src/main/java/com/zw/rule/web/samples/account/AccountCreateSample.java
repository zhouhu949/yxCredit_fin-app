package com.zw.rule.web.samples.account;

import com.junziqian.api.common.Constants;
import com.junziqian.api.common.FileUtils;
import com.junziqian.api.common.org.OrganizationType;
import com.junziqian.api.request.builder.OrganizationCreateBuilder;
import com.junziqian.api.response.OrganizationCreateResponse;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;

/**
 * Created by wlinguo on 2016-07-01.
 */
public class AccountCreateSample extends JunziqianClientInit {

    static String img = "C:\\Users\\dp\\Desktop\\图片\\IMG_2531.JPG";

    public static void main(String[] args) {
        OrganizationCreateBuilder builder = new OrganizationCreateBuilder();
        builder.withEmailOrMobile("wlinguo@mail.bccto.me");
        builder.withName("文林果测试企业");
        builder.withLegalName("");
        builder.withLegalIdentityCard("");
        builder.withLegalMobile("");

        builder.withIdentificationType(Constants.IDENTIFICATION_TYPE_TRADITIONAL);
        builder.withOrganizationType(OrganizationType.ENTERPRISE.getCode());
        builder.withOrganizationRegNo("500903000035444");
        builder.withOrganizationRegImg(FileUtils.uploadFile(img));
        builder.withOrganizationCode("58016467-6");
        builder.withOrganizationCodeImg(FileUtils.uploadFile(img));
        builder.withTaxCertificateImg(FileUtils.uploadFile(img));
        builder.withSignApplication(FileUtils.uploadFile(img));
        builder.withSignImg(FileUtils.uploadFile(img));
        OrganizationCreateResponse response = getClient("","","").organizationCreate(builder.build());
        LogUtils.logResponse(response);
    }

}
