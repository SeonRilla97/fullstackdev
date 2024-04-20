package seon.full.mallapi.util;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomFileUtil {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);

        if(tempFolder.exists() == false) {
            tempFolder.mkdir();
        }

         uploadPath= tempFolder.getAbsolutePath();
        log.info("===========================");
        log.info(uploadPath);
    }

    /**
     * 파일 저장
     * @param files
     * @return
     * @throws IOException
     */
    public List<String> saveFiles(List<MultipartFile> files) throws IOException {
        if(files == null || files.size() ==0) {
            return List.of();
        }

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            String savedName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();


            Path savepath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(multipartFile.getInputStream(), savepath);

                String contentType = multipartFile.getContentType();

                
                if(contentType != null && contentType.startsWith("image")){
                    //이미지 일 경우, 썸네일로 제작 (용량 낮추기)
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);

                    Thumbnails.of(savepath.toFile())
                            .size(200, 200)
                            .toFile(thumbnailPath.toFile());
                }
                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return uploadNames;
    }

    /**
     * 파일 조회
     * @param fileName
     * @return
     */
    public ResponseEntity<Resource> getFiles(String fileName)  {
        // 리소스의 위치를 의미
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        //리소스를 읽을 수 없다면 기본 설정 된 파일을 읽는다.
        if( ! resource.isReadable()){
            resource = new FileSystemResource(uploadPath + File.separator + "default.png");
        }

        // Http 헤더 선언
        HttpHeaders headers = new HttpHeaders();

        try{
            //Header에 대상 파일의 Content-Type을 파악한다.
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch (IOException e) {
            
            //에러 발생시 internalServerError 를 반환
            return ResponseEntity.internalServerError().build();
        }
        //아니면 응답에 헤더를 실어서 보낸다 ( ContentType 정보 + 첨부파일 전송 )
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /**
     * 파일 삭제
     * @param fileNames
     */
    public void deleteFiles(List<String> fileNames) {
        if(fileNames == null || fileNames.size() == 0 ) {
            return;
        }

        fileNames.forEach(fileName -> {
            String thumbnailFileName = "s_" + fileName;
            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try{
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            }catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
