package com.aone.menurandomchoice.views.menuregister;

import com.aone.menurandomchoice.R;

enum RegisterState {
    SUCCESS(R.string.register_success),
    NO_IMAGE(R.string.register_no_image),
    NO_NAME(R.string.register_no_name),
    NO_DESCRIPTION(R.string.register_no_description),
    NO_PRICE(R.string.register_no_price),
    NO_CATEGORY(R.string.register_no_category);

    private int messageResourceId;

    RegisterState(int messageResourceId) {
        this.messageResourceId = messageResourceId;
    }

    public int getMessageResourceId() {
        return messageResourceId;
    }

}
