package com.zdata.common;

public class MyMessageListener implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(MyMessageListener.class);
	
	@Override
	public void onMessage(Message msg) {
		 if (msg instanceof TextMessage) {
            try {
                TextMessage txtMsg = (TextMessage) msg;
                String message = txtMsg.getText();
                LOG.info("msg:" + message);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
	}

}
