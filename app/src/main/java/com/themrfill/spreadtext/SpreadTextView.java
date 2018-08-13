package com.themrfill.spreadtext;


import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip Arnold on 04 Aug 2018.
 * This class is an extension of the default AppCompatTextView rather than TextView for compatability)
 * which spreads text over the number of lines to give a more pleasing aesthetic than having a
 * single line (or multiple lines) of long text and then a much shorter line - this looks better
 * when used either centred or on its own to the side.
 * It uses a "\n" to break the lines rather than forcing the width of the view to change size.
 * As long as the width is defined as "wrap_content" it will shrink to size allowing other views
 * to fit closer to the text.
 */

public class SpreadTextView extends android.support.v7.widget.AppCompatTextView {
	public SpreadTextView(Context context) {
		super(context);
	}

	public SpreadTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SpreadTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		relayout();
	}

	private void relayout() {
		if (getLineCount() > 1) {
			Paint paint = getPaint();
			paint.setTextSize(getTextSize());
			float len = paint.measureText(getText().toString());
			float avg = len / getLineCount();

			List<String> lines = new ArrayList<>();
			String cont = getText().toString();
			cont = cont.replace("\n", " ");
			String[] words = cont.split(" ");

			StringBuilder replace = new StringBuilder(words[0]);
			for (int i = 1; i < words.length; i++) {
				if (replace.length() > 0)
					replace.append(" ");
				replace.append(words[i]);
				if (paint.measureText(replace.toString()) > avg) {
					lines.add(replace.toString());
					replace = new StringBuilder();
				}
			}
			if (replace.length() > 0)
				lines.add(replace.toString());
			replace = new StringBuilder();
			for (String line : lines) {
				if (replace.length() > 0)
					replace.append("\n");
				replace.append(line);
			}
			setText(replace);
			invalidate();
		}
	}
}
