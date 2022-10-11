package com.zerobase.zerolms.course.controller;

import com.zerobase.zerolms.admin.service.CategoryService;
import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.model.CourseInput;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;
    @GetMapping("/admin/course/list")
    public String courseList(Model model, CourseParam parameter) {

        parameter.init();
        List<CourseDto> courseList = courseService.list(parameter);

        long totalCount = 0;

        if(!CollectionUtils.isEmpty(courseList)){
            totalCount = courseList.get(0).getTotalCount();
        }


        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount,parameter.getPageSize(),parameter.getPageIndex(),parameter.getQueryString());

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager",pagerHtml);



        return "admin/course/list";
    }

    @GetMapping(value={"/admin/course/add","/admin/course/edit"})
    public String add(Model model, HttpServletRequest req
    ,CourseInput param) {


        model.addAttribute("category",categoryService.selectList());

        //category 정보 필요
        boolean isEdit = req.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();

        if(isEdit){
            long id = param.getId();
            CourseDto existCourse = courseService.getById(id);
            if(existCourse == null){
            model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }

            detail = existCourse;
        }
        model.addAttribute("isEdit",isEdit);
        model.addAttribute("detail",detail);

        return "admin/course/add";
    }




    @PostMapping(value = {"/admin/course/add","/admin/course/edit"})
    public String addSubmit(Model model, CourseInput param
    , HttpServletRequest req, MultipartFile file) {

        String saveFileName ="";
        String urlFileName="";

        if(file != null){

            String originalFileName = file.getOriginalFilename();
            System.out.println(originalFileName);

            String baseLocalPath="C:\\zero\\Ultimate\\zeroLms\\src\\main\\resources\\static\\files";
            String baseUrlPath="/files";
            String [] arrFileName= getNewSaveFile(baseUrlPath,baseLocalPath,originalFileName);

             saveFileName = arrFileName[0];
             urlFileName = arrFileName[1];

            try{
                File newFile = new File(saveFileName);
                FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(newFile));

            }catch(IOException e){
            log.info("#############################");
            log.info(e.getMessage());
            }
        }

        param.setFileName(saveFileName);
        param.setUrlFileName(urlFileName);

        /*list<MultipartFile> 하면 여러개 한번에 받기 가능*/
        boolean isEdit = req.getRequestURI().contains("/edit");

        if(isEdit){
            long id = param.getId();

            CourseDto existCourse = courseService.getById(id);
            if(existCourse == null){
                model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(param);
        }else{
            boolean result = courseService.add(param);
        }

        return "redirect:/admin/course/list";
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

    @PostMapping("/admin/course/delete")
    public String deleteSubmit(Model model, CourseInput param
            ,HttpServletRequest req) {

        System.out.println(param.getIdList());
        boolean result = courseService.del(param.getIdList());


        return "redirect:/admin/course/list";
    }


}
