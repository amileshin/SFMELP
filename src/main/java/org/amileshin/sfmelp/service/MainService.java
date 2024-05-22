package org.amileshin.sfmelp.service;

import com.clickhouse.client.ClickHouseClient;
import com.clickhouse.client.ClickHouseNodes;
import com.clickhouse.client.ClickHouseProtocol;
import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {
    public List<String> getConnect(ConnectInfoDTO info) {
        ClickHouseNodes nodes = ClickHouseNodes.of(
                "http://"+ info.getHost()+ ":" + info.getPort() + "/" + info.getDatabase());
        try (ClickHouseClient client = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP);
            ClickHouseResponce responce = client.read(nodes);
        ){

        }
    }
}
