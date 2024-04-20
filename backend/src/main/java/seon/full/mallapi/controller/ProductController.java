package seon.full.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seon.full.mallapi.service.ProductService;
import seon.full.mallapi.dto.PageRequestDTO;
import seon.full.mallapi.dto.PageResponseDTO;
import seon.full.mallapi.dto.ProductDTO;
import seon.full.mallapi.util.CustomFileUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;

    /**
     * 상품 등록 / POST
     */
    @PostMapping("/")
    public Map<String, Long> register( ProductDTO productDTO) throws IOException {
        log.info("register: " + productDTO);

        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info("Upload FIle Name : " + uploadFileNames);

        Long pno = productService.register(productDTO);

        return Map.of("RESULT", pno);
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String filename) {
        return fileUtil.getFiles(filename);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')") //
    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("list..............." + pageRequestDTO);

        return productService.getList(pageRequestDTO);
    }


    @GetMapping("/{pno}")
    public ProductDTO list(@PathVariable(name = "pno") Long pno) {
        return productService.get(pno);
    }


    @PutMapping("/{pno}")
    public Map<String, String> Modify(@PathVariable(name = "pno") Long pno, ProductDTO productDTO) throws IOException {

        productDTO.setPno(pno);

        ProductDTO oldProductDTO = productService.get(pno);
        //기존 파일들 (DB 저장 된것들)
        List<String> oldFileNames = oldProductDTO.getUploadFileNames();
        //새로 업로드 할 파일
        List<MultipartFile> files = productDTO.getFiles();
        //새로 업로드 되어 만들어진 파일들
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);
        //기존 파일 이름
        List<String> uploadFileNames = productDTO.getUploadFileNames();

        // 기존 파일 + 새로운 파일
        if (currentUploadFileNames != null && currentUploadFileNames.size() > 0) {
            uploadFileNames.addAll(currentUploadFileNames);
        }

        // DB수정
        productService.modify(productDTO);

        if (oldFileNames != null && oldFileNames.size() > 0) {

            // 지울 파일 찾기
            // 예전 파일 중 지울 것 찾기 ( 기존 리스트 & 새로운 리스트 비교하여 없는것 제거
            List<String> removeFiles = oldFileNames
                    .stream()
                    .filter(fileName -> uploadFileNames.indexOf(fileName) == -1)
                    .collect(Collectors.toList());

            fileUtil.deleteFiles(removeFiles);
        }

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{pno}")
    public Map<String, String> remove (@PathVariable("pno") Long pno){
        List<String> oldFileNames = productService.get(pno).getUploadFileNames();

        productService.remove(pno);

        fileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "SUCCESS");

}

}

