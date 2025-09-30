package com.bsrubacky.tapeeater.ui.helpers

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert
import androidx.core.text.isDigitsOnly

class LengthOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if(length>0) insert(length,"s")
        if(length > 3) insert(length-3, "m ")
        if(length > 7) insert(length-7, "h ")
    }
}

class PositionTransformation(val size: Int, val isEdit: Boolean) : InputTransformation{
    override fun TextFieldBuffer.transformInput() {
        if (!asCharSequence().isDigitsOnly()) {
            revertAllChanges()
        }
        if(asCharSequence().isNotBlank()){
            val position = asCharSequence().toString().toInt()
            if(isEdit){
                if(position>size||position==0){
                    revertAllChanges()
                }
            }else{
                if(position>size+1||position==0){
                    revertAllChanges()
                }
            }
        }
    }
}

class NumberTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (!asCharSequence().isDigitsOnly()) {
            revertAllChanges()
        }
    }
}
