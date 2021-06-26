package com.sybinal.shop.service.sequence;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sybinal.shop.mapper.SequenceMapper;
import com.sybinal.shop.model.Sequence;
import com.sybinal.shop.model.SequenceKey;
import com.sybinal.shop.utils.Constants;

@Service("sequenceService")
@Lazy
public class SequenceServiceImpl implements SequenceService {

	@Autowired
	SequenceMapper sequenceMapper;

	@Autowired
	PlatformTransactionManager transactionManager;

	private final Lock lock = new ReentrantLock();

	@Override
	public String getSequence(String typeCode, String counterKey, int len) {
		String autoNo = "";
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			autoNo = this.getSequenceMain(typeCode, counterKey, len);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			return Constants.AUTO_ERROR;
		}
		return autoNo;
	}

	private String getSequenceMain(String typeCode, String counterKey, int len) throws Exception {
		lock.lock();
		String autoNo = "";
		try {
			SequenceKey key = new SequenceKey();
			key.setTypeCode(typeCode);
			key.setCounterKey(counterKey);
			Sequence sequence = sequenceMapper.selectByPrimaryKey(key);
			if (sequence == null) {
				sequence = new Sequence();
				sequence.setTypeCode(typeCode);
				sequence.setCounterKey(counterKey);
				sequence.setNoCounter(1);
				sequenceMapper.insert(sequence);
				autoNo = counterKey + StringUtils.leftPad("1", len, "0");
			} else {
				int cnt = sequence.getNoCounter() + 1;
				int max = Integer.parseInt(StringUtils.rightPad("1", len + 1, "0"));
				if (max < cnt) {
					throw new Exception();
				}
				sequence.setNoCounter(cnt);
				sequenceMapper.updateByPrimaryKey(sequence);
				autoNo = counterKey + StringUtils.leftPad(String.valueOf(cnt), len, "0");
			}
		} finally {
			lock.unlock();
		}
		return autoNo;
	}

}
