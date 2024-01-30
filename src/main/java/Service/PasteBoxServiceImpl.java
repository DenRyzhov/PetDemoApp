package Service;

import api.request.PasteBoxRequest;
import api.request.PublicStatus;
import api.request.response.PasteBoxResponse;
import api.request.response.PasteBoxUrlResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import repository.PasteBoxEntity;
import repository.PasteBoxRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
@ConfigurationProperties(prefix = "app")


public class PasteBoxServiceImpl implements PasteBoxService {

    private String host = "https://abc.ru";
    private int publicListSize = 10;

    private final PasteBoxRepository repository;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {

        PasteBoxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBoxes(int amount) {
        List<PasteBoxEntity> list = repository.getListOfPublicAndAlive(publicListSize);
        return list.stream().map(pasteBoxEntity -> new PasteBoxResponse(pasteBoxEntity.getData(),
                        pasteBoxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBoxes() {
        return null;
    }


    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBoxEntity.setLifeTime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteBoxEntity);

        return new PasteBoxUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
