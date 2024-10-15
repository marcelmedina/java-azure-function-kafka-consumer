package com.function;

import com.microsoft.azure.functions.BrokerAuthenticationMode;
import com.microsoft.azure.functions.BrokerProtocol;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.ExponentialBackoffRetry;
import com.microsoft.azure.functions.annotation.FixedDelayRetry;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.KafkaTrigger;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    @FunctionName("KafkaTriggerRetry")
    @FixedDelayRetry(maxRetryCount = 3, delayInterval = "00:00:02")
    public void runSingle(
            @KafkaTrigger(name = "KafkaTriggerRetry", topic = "%topic%", brokerList = "%BrokerList%", consumerGroup = "$Default", username = "%ConfluentCloudUsername%", password = "%ConfluentCloudPassword%", authenticationMode = BrokerAuthenticationMode.PLAIN, protocol = BrokerProtocol.SASLSSL, dataType = "string") String kafkaEventData,
            final ExecutionContext context) throws Exception {
        context.getLogger().info(kafkaEventData);
        throw new Exception("Unhandled Error");
    }

    @FunctionName("KafkaTriggerExponentialRetry")
    @ExponentialBackoffRetry(maxRetryCount = -1, minimumInterval = "00:00:05", maximumInterval = "00:01:00")
    public void runExponentialRetry(
            @KafkaTrigger(name = "KafkaTriggerExponentialRetry", topic = "%topic-output%", brokerList = "%BrokerList%", consumerGroup = "$Default", username = "ConfluentCloudUsername", password = "%ConfluentCloudPassword%", authenticationMode = BrokerAuthenticationMode.PLAIN, protocol = BrokerProtocol.SASLSSL, dataType = "string") String kafkaEventData,
            final ExecutionContext context) throws Exception {
        context.getLogger().info(kafkaEventData);
        throw new Exception("Unhandled Error");
    }
}
