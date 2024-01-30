package Service;

import api.request.PasteBoxRequest;
import api.request.response.PasteBoxResponse;
import api.request.response.PasteBoxUrlResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PasteBoxService {
    PasteBoxResponse getByHash(String hash);
    List<PasteBoxResponse> getFirstPublicPasteBoxes(int amount);

    List<PasteBoxResponse> getFirstPublicPasteBoxes();

    PasteBoxUrlResponse create(PasteBoxRequest request);
}
