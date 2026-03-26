package org.gaborbalazs.transactioninfo;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
class TransactionInfoDao {

    private static final Map<Integer, TransactionInfo> TRANSACTION_INFO_MAP = Map.of(
            1, new TransactionInfo(1, "Revolut transaction", "Sending money for Valhalla Project"),
            2, new TransactionInfo(2, "Wise transaction", "Sending money for Atari to be great again"),
            3, new TransactionInfo(1, "Lightyear transaction", "Sending money to buy some Nvidia shares")
    );

    TransactionInfo findById(Integer id) {
        return TRANSACTION_INFO_MAP.get(id);
    }
}
