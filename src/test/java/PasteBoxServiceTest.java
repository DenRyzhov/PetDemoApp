import Exeption.NotFoundEntityExeption;
import Service.PasteBoxService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RequiredArgsConstructor
@SpringBootTest
public class PasteBoxServiceTest {
    @Autowired
    private final PasteBoxService pasteBoxService;

    @Test
    public void notExistHash(){
        assertThrows(NotFoundEntityExeption.class, ()-> pasteBoxService.getByHash("sdadasdad"));
    }
}
