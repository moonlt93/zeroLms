package com.zerobase.zerolms.admin.controller;

import com.zerobase.zerolms.admin.dto.BannerDto;
import com.zerobase.zerolms.admin.dto.CategoryDto;
import com.zerobase.zerolms.admin.model.BannerParam;
import com.zerobase.zerolms.admin.model.CategoryInput;
import com.zerobase.zerolms.admin.model.MemberAdInput;
import com.zerobase.zerolms.admin.model.MemberParam;
import com.zerobase.zerolms.admin.service.BannerService;
import com.zerobase.zerolms.admin.service.CategoryService;
import com.zerobase.zerolms.course.model.CourseInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminBannerController {

    private final CategoryService categoryService;
    private final BannerService bannerService;


    @GetMapping("/admin/banner/list")
    public String BannerList(Model model, BannerParam param) {

        List<BannerDto> list = bannerService.frontList(param);

        long totalCount = param.getTotalCount();
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("list", list);

        return "/admin/banner/list";
    }

    @GetMapping(value = {"/admin/banner/add", "/admin/banner/edit"})
    public String GetPicAdd(Model model, BannerParam param, HttpServletRequest req
            , MultipartFile file) {

        /*        model.addAttribute("list",bannerService.selectList(param));*/

        boolean isEdit = req.getRequestURI().contains("/edit");
        BannerDto banner = new BannerDto();
        if (isEdit) {
            long id = param.getId();
            BannerDto existBanner = bannerService.getById(id);
            if (existBanner == null) {
                model.addAttribute("message", "배너가존재하지 않습니다.");
                return "common/error";
            }
            banner = existBanner;
        }
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("detail", banner);

        return "admin/banner/add";

    }


    @PostMapping(value = {"/admin/banner/add", "/admin/banner/edit"})
    public String PicAdd(Model model, BannerParam param,
                         HttpServletRequest req, MultipartFile file) {

        String bannerFile = "";
        String bannerLink = "";
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            String baseLocalPath = "C:\\zero\\Ultimate\\zeroLms\\src\\main\\resources\\static\\banner";
            String baseUrlPath = "/banner";



                String[] arrFileName = getNewSaveFile(baseUrlPath, baseLocalPath, originalFileName);


            bannerFile = arrFileName[0];
            bannerLink = arrFileName[1];

            try {
                File newFile = new File(bannerFile);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));

            } catch (IOException e) {
                log.info("#####################");
                log.info(e.getMessage());
            }

        }

        param.setBannerFile(bannerFile);
        param.setBannerLink(bannerLink);

        boolean Editing = req.getRequestURI().contains("/edit");

        if (Editing) {
            long id = param.getId();

            BannerDto banner = bannerService.getById(id);

            if (banner == null) {
                model.addAttribute("message", "배너가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = bannerService.set(param);
        } else {

            boolean result = bannerService.addPic(param);
        }
        return "redirect:/admin/banner/list";

    }

    private String[] getNewSaveFile(String baseUrlPath,String baseLocalPath,String OriginalName){
        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath,now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir: dirs){
            File file = new File(dir);
            if(!file.isDirectory()){
                //디렉토리가 없으면 생성
                file.mkdir();
            }
        }


        String fileExtension=" ";
        if(OriginalName != null){
            int dotPos = OriginalName.lastIndexOf(".");
            if(dotPos > -1){
                fileExtension =OriginalName.substring(dotPos+1);
            }
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String newFilename= String.format("%s%s",dirs[2],uuid);
        String urlFilename = String.format("%s%s",urlDir,uuid);

        if(fileExtension.length()>0){
            newFilename+="."+fileExtension;
            urlFilename+="."+fileExtension;
        }

        return new String []{newFilename,urlFilename};

    }


    @PostMapping("/admin/banner/status")
    public String status(Model model, BannerParam param){

        boolean result= bannerService.updateStatus(param.getBannerStatus(),param.getId());


        return "redirect:/admin/banner/edit?id="+param.getId();
    }

}

