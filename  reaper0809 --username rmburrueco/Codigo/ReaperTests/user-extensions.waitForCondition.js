// Waits for the condition to be "true"
Selenium.prototype.doWaitForCondition = function(script, timeout) {
    if (isNaN(timeout)) {
    	throw new SeleniumError("Timeout is not a number: " + timeout);
    }
    
    testLoop.waitForCondition = function () {
        return eval(script);
    };
    
    testLoop.waitForConditionStart = new Date().getTime();
    testLoop.waitForConditionTimeout = timeout;
    
    testLoop.pollUntilConditionIsTrue = function () {
        try {
	        if (this.waitForCondition()) {
	            this.waitForCondition = null;
	            this.waitForConditionStart = null;
	            this.waitForConditionTimeout = null;
	            this.continueCommandExecutionWithDelay();
	        } else {
	        	if (this.waitForConditionTimeout != null) {
		        	var now = new Date();
		        	if ((now - this.waitForConditionStart) > this.waitForConditionTimeout) {
		        		throw new SeleniumError("Timed out after " + this.waitForConditionTimeout + "ms");
		        	}
		        }
	            window.setTimeout("testLoop.pollUntilConditionIsTrue()", 10);
	        }
	    } catch (e) {
	    	var lastResult = new CommandResult();
    		lastResult.failed = true;
    		lastResult.failureMessage = e.message;
	    	this.commandComplete(lastResult);
	    	this.testComplete();
	    }
    };
};
