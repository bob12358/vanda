package com.vanda.vandalibnetwork.fragment;

import java.util.Map;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vanda.vandalibnetwork.daterequest.GsonRequest;
import com.vanda.vandalibnetwork.daterequest.RequestManager;

/**
 * @author vanda 伍中联 实现了Fragment 请求网络的封装
 *
 * @param <T>
 */
public abstract class BaseFragment<T> extends SherlockFragment {

	@Override
	public void onStop() {
		super.onStop();
		RequestManager.cancelAll(BaseFragment.this);
	}

	protected void executeRequest(Request<T> request) {
		RequestManager.addRequest(request, BaseFragment.this);
	}

	protected void processData(T response) {
		RequestManager.cancelAll(BaseFragment.this);
	}

	protected void errorData(VolleyError volleyError) {
		RequestManager.cancelAll(BaseFragment.this);
	}

	protected abstract String getRequestUrl();

	protected abstract Class<T> getResponseDataClass();

	protected abstract Map<String, String> getParamMap();

	public void startExecuteRequest(int method) {
		executeRequest(new GsonRequest<T>(method, getRequestUrl(),
				getResponseDataClass(), getParamMap(),
				new Response.Listener<T>() {
					@Override
					public void onResponse(final T response) {
						processData(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						errorData(volleyError);
					}
				}));
	}
}
