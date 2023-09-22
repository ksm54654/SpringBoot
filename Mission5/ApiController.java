@RestController
public class ApiController {

    @PostMapping(value = "/send_csv", produces = "text/csv")
    public ResponseEntity<byte[]> sendCsv() throws IOException {
        ClassPathResource resource = new ClassPathResource("data.csv");
        byte[] csvBytes = Files.readAllBytes(Path.of(resource.getURI()));

        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=data.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }

    @PostMapping("/receive_and_process")
    public String receiveAndProcessCsv(@RequestBody MultipartFile file) {
        try {
            // 받은 파일을 로컬에 저장 (여기서는 임시로 처리)
            file.transferTo(Path.of("src/main/resources/received_data.csv"));

            // 모델 적용 및 결과 계산
            String result = "모델 적용 결과";

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "에러 발생";
        }
    }
}
