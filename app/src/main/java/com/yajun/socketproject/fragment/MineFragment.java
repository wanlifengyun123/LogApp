package com.yajun.socketproject.fragment;

import com.example.market.R;
import com.example.market.activity.FavorActivity;
import com.example.market.activity.HistoryActivity;
import com.example.market.activity.LoginActivity;
import com.example.market.activity.MainActivity;
import com.example.market.activity.MessageCenterActivity;
import com.example.market.activity.MoreActivity;
import com.example.market.activity.OrdersActivity;
import com.example.market.activity.PurseActivity;
import com.example.market.activity.WebActivity;
import com.example.market.utils.Constants;
import com.lib.uil.UILUtils;
import com.umeng.fb.FeedbackAgent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class MineFragment extends Fragment implements OnClickListener {

	private View layout;
	private View mViewNotLogined;
	private View mViewLogined;
	private TextView mTvUid;
	private ImageView mImgUserIcon;
	private String uid;
	private String icon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (layout != null) {
			initLogin();
			// ��ֹ���new��Ƭ�ζ������ͼƬ��������
			return layout;
		}
		layout = inflater.inflate(R.layout.fragment_mine, container, false);
		initView();
		setOnListener();
		initLogin();
		return layout;
	}

	/**
	 * ��ʼ����¼��Ϣ
	 * 
	 * @param activity
	 * @param isLogined
	 */
	private void initLogin() {
		MainActivity activity = (MainActivity) getActivity();
		boolean isLogined = activity.getLogined();
		if (isLogined) {
			// ��ȡ��¼����
			SharedPreferences sp = activity.getSharedPreferences("login_type",
					Context.MODE_PRIVATE);
			int type = sp.getInt("login_type", 0);
			switch (type) {
			case 1: // ͨ��Bmob��¼
				break;
			case 2: // ͨ��΢����¼
				icon = activity.getIcon();
				UILUtils.displayImage(getActivity(), icon, mImgUserIcon);
				break;

			default:
				break;
			}
			uid = activity.getUid();
			mViewNotLogined.setVisibility(View.GONE);
			mViewLogined.setVisibility(View.VISIBLE);
			mTvUid.setText(uid);
		} else {
			mViewNotLogined.setVisibility(View.VISIBLE);
			mViewLogined.setVisibility(View.GONE);
		}
	}

	private void setOnListener() {
		layout.findViewById(R.id.layout_mine_order).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_wallet).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_messages).setOnClickListener(this);
		layout.findViewById(R.id.personal_login_button)
				.setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_feedback).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_collects).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_history).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_appoint).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_account_center).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_service_manager).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_discuss).setOnClickListener(this);
		layout.findViewById(R.id.layout_mine_android_my_jd_assitant)
				.setOnClickListener(this);
		layout.findViewById(R.id.tv_more).setOnClickListener(this);
	}

	private void initView() {
		mViewNotLogined = layout.findViewById(R.id.layout_not_logined);
		mViewLogined = layout.findViewById(R.id.layout_logined);
		mTvUid = (TextView) layout.findViewById(R.id.tv_uid);
		mImgUserIcon = (ImageView) layout.findViewById(R.id.user_icon);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// ��layout�Ӹ�������Ƴ�
		ViewGroup parent = (ViewGroup) layout.getParent();
		parent.removeView(layout);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.INTENT_KEY.LOGIN_REQUEST_CODE) {
			if (resultCode == Constants.INTENT_KEY.LOGIN_RESULT_SUCCESS_CODE) {
				SharedPreferences sp = getActivity().getSharedPreferences(
						"login_type", Context.MODE_PRIVATE);
				int type = sp.getInt("login_type", 0);
				String uid = "";
				String icon = "";
				switch (type) {
				case 1:
					uid = data.getStringExtra("uid");
					break;
				case 2:
					uid = data.getStringExtra("screen_name");
					icon = data.getStringExtra("profile_image_url");
					UILUtils.displayImage(getActivity(), icon, mImgUserIcon);
					break;

				default:
					break;
				}
				mTvUid.setText(uid);
				mViewNotLogined.setVisibility(View.GONE);
				mViewLogined.setVisibility(View.VISIBLE);
				// ����¼������ø�MainActivity
				MainActivity activity = (MainActivity) getActivity();
				activity.setIsLogined(true, uid, icon);
			}
		} else if (requestCode == Constants.INTENT_KEY.REQUEST_MOREACTIVITY) {
			initLogin();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_login_button: // ��¼
			login();
			break;
		case R.id.layout_mine_feedback: // �����
			new FeedbackAgent(getActivity()).startFeedbackActivity();
			break;
		case R.id.layout_mine_android_my_jd_assitant: // ���ķ���
			Intent intent = new Intent(getActivity(), WebActivity.class);
			intent.putExtra("direction", 5);
			startActivity(intent);
			break;
		case R.id.tv_more: // ���
			Intent intent2 = new Intent(getActivity(), MoreActivity.class);
			startActivityForResult(intent2,
					Constants.INTENT_KEY.REQUEST_MOREACTIVITY);
			break;
		case R.id.layout_mine_order: // ȫ������
			startActivity(new Intent(getActivity(), OrdersActivity.class));
			break;
		case R.id.layout_mine_wallet: // �ҵ�Ǯ��
			startActivity(new Intent(getActivity(), PurseActivity.class));
			break;
		case R.id.layout_mine_messages: // �ҵ���Ϣ
			startActivity(new Intent(getActivity(), MessageCenterActivity.class));
			break;
		case R.id.layout_mine_collects: // �ҵĹ�ע
			startActivity(new Intent(getActivity(), FavorActivity.class));
			break;
		case R.id.layout_mine_history: // �����¼
			startActivity(new Intent(getActivity(), HistoryActivity.class));
			break;
		case R.id.layout_mine_appoint: // �ҵ�ԤԼ
			Intent intent3 = new Intent(getActivity(), WebActivity.class);
			intent3.putExtra("direction", 9);
			startActivity(intent3);
			break;
		case R.id.layout_mine_account_center: // �˻��밲ȫ
			Intent intent4 = new Intent(getActivity(), WebActivity.class);
			intent4.putExtra("direction", 12);
			startActivity(intent4);
			break;
		case R.id.layout_mine_service_manager: // ����ܼ�
			Intent intent5 = new Intent(getActivity(), WebActivity.class);
			intent5.putExtra("direction", 10);
			startActivity(intent5);
			break;
		case R.id.layout_mine_discuss: // ������Ʒ
			Intent intent6 = new Intent(getActivity(), WebActivity.class);
			intent6.putExtra("direction", 11);
			startActivity(intent6);
			break;

		default:
			break;
		}
	}

	private void login() {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivityForResult(intent, Constants.INTENT_KEY.LOGIN_REQUEST_CODE);
	}

}
