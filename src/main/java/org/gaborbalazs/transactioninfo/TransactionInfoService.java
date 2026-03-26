package org.gaborbalazs.transactioninfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TransactionInfoService {

    private final TransactionInfoDao transactionInfoDao;

    TransactionInfo get(Integer id) {
        return transactionInfoDao.findById(id);
    }
}
