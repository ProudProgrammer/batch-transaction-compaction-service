package org.gaborbalazs.transactioninfo;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
class TransactionInfoController {

    private final TransactionInfoService transactionInfoService;

    @QueryMapping
    TransactionInfo transactionInfoById(@Argument Integer id) {
        return transactionInfoService.get(id);
    }
}
