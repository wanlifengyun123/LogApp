package com.yajun.socketproject.fragment;

import com.example.market.R;
import com.example.market.activity.BoxActivity;
import com.example.market.activity.DetailActivity;
import com.example.market.activity.FavorActivity;
import com.example.market.activity.MainActivity;
import com.example.market.activity.OrdersActivity;
import com.example.market.activity.PurseActivity;
import com.example.market.activity.SearchActivity;
import com.example.market.activity.WebActivity;
import com.example.market.bean.GoodsInfo;
import com.example.market.utils.Constants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nineoldandroids.animation.ObjectAnimator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class HomeFragment extends Fragment implements OnClickListener {

	private PullToRefreshScrollView mPtrScrollView;

	private ViewPager mPager;
	private int[] mBanner = new int[] { R.drawable.img_home_banner1,
			R.drawable.img_home_banner2, R.drawable.img_home_banner3,
			R.drawable.img_home_banner4 };
	private GoodsInfo[] mInfos;
	private ImageView mImageView;
	private ImageView mImgCover;
	private int mLastPos;// ��¼��һ��ViewPager��λ��
	private boolean isDragging;// �Ƿ���ק
	private boolean isFoucusRight; // ScrollView�Ƿ�������Ҳ�
	private View layout;
	private int secondCount = 10800; // ����ʱ3Сʱ

	private HorizontalScrollView mScrollView;
	private HorizontalScrollView mScrollView2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initGoodsInfos();
		if (layout != null) {
			// ��ֹ���new��Ƭ�ζ������ͼƬ��������
			return layout;
		}
		layout = inflater.inflate(R.layout.fragment_home, container, false);
		initView();
		initPager();
		autoScroll();
		initTimer();
		return layout;
	}

	private void initGoodsInfos() {
		mInfos = new GoodsInfo[] {
				new GoodsInfo(
						"100001",
						"Levi's��ά˹��ʿ����ʱ�г�������T��82176-0005 ��/�� L",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods01.jpg",
						"����Ь��", 153.00, "����96%", 1224, 1, 0),
				new GoodsInfo(
						"100002",
						"Levi's��ά˹505ϵ����ʿ����ֱ��ţ�п�00505-1185 ţ��ɫ 36 34",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods02.jpg",
						"����Ь��", 479.00, "����95%", 645, 0, 0),
				new GoodsInfo(
						"100003",
						"GXG��װ ����ר�� 2015��װ�¿� ��ʿʱ�а�ɫ����Բ�����T��#42244315 ��ɫ M",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods03.jpg",
						"����Ь��", 149.00, "��������", 1856, 0, 0),
				new GoodsInfo(
						"100004",
						"Apple iPad mini ME276CH/A �䱸 Retina ��ʾ�� 7.9Ӣ��ƽ����� ��16G WiFi�棩��ջ�ɫ",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods04.jpg",
						"��������", 2138.00, "����97%", 865, 0, 0),
				new GoodsInfo(
						"100005",
						"���루ThinkPad���ᱡϵ��E450C(008CD) 14Ӣ��ʼǱ����� ��i3-4005U 4GB 500G+8GSSD 1G WIN8.1��",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods05.jpg",
						"��������", 3299.00, "����95%", 236, 0, 0),
				new GoodsInfo(
						"100006",
						"�޼���Logitech��G502 ����Ӧ��Ϸ���",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods06.jpg",
						"����Ь��", 499.00, "����95%", 115, 0, 0),
				new GoodsInfo(
						"100007",
						"��ʿ��Swissgear��SA7777WH 12Ӣ��ʱ��������˫����Ա��� �װ�ɫ",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods07.jpg",
						"����Ь��", 199.00, "����95%", 745, 0, 0),
				new GoodsInfo(
						"100008",
						"����Transcend�� 340ϵ�� 256G SATA3 ��̬Ӳ��(TS256GSSD340)",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods08.jpg",
						"��������", 569.00, "����95%", 854, 1, 0),
				new GoodsInfo(
						"100009",
						"���ܣ�Canon�� EOS 700D �����׻� ��EF-S 18-135mm f/3.5-5.6 IS STM��ͷ��",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods09.jpg",
						"��������", 5099.00, "����94%", 991, 0, 0),
				new GoodsInfo(
						"100010",
						"��������F-WHEEL) ���ܵ綯���ֳ� ��ƽ����ֳ� ����ϵ������ ֧�� ���� ���� ��ɫD1��20KM��֧��",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods10.jpg",
						"�˶�����", 2999.00, "����93%", 1145, 0, 0),
				new GoodsInfo(
						"100011",
						"����21��26�����Ͻ����г� ����ŵ���� ����ɵ���������ɽ�س� QJ243 ��Ӫ",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods11.jpg",
						"�˶�����", 1088.00, "����92%", 909, 0, 0),
				new GoodsInfo(
						"100012",
						"���Ƕ�һ������������ ��Ӫ",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods12.jpg",
						"ͼ������", 25.40, "����95%", 1443, 0, 0),
				new GoodsInfo(
						"100013",
						"����Զ��",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods13.jpg",
						"ͼ������", 19.70, "����98%", 3702, 0, 0),
				new GoodsInfo(
						"100014",
						"���ڵ�����",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods14.jpg",
						"ͼ������", 38.40, "����97%", 442, 1, 0),
				new GoodsInfo(
						"100015",
						"Photoshopרҵ��ͼ���� �����1��",
						"http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods15.jpg",
						"ͼ������", 57.80, "����93%", 765, 0, 0) };
	}

	private void initView() {
		layout.findViewById(R.id.img_home_category).setOnClickListener(this);
		layout.findViewById(R.id.img_home_search_code).setOnClickListener(this);
		layout.findViewById(R.id.layout_my_focus).setOnClickListener(this);
		layout.findViewById(R.id.layout_logistics).setOnClickListener(this);
		layout.findViewById(R.id.layout_top_up).setOnClickListener(this);
		layout.findViewById(R.id.layout_film).setOnClickListener(this);
		layout.findViewById(R.id.layout_game_top_up).setOnClickListener(this);
		layout.findViewById(R.id.layout_purse).setOnClickListener(this);
		layout.findViewById(R.id.layout_jingdou).setOnClickListener(this);
		layout.findViewById(R.id.layout_more).setOnClickListener(this);
		layout.findViewById(R.id.layout_home_search).setOnClickListener(this);
		layout.findViewById(R.id.layout_discount).setOnClickListener(this);
		layout.findViewById(R.id.layout_discount_phone).setOnClickListener(this);
		layout.findViewById(R.id.img_discount_banner).setOnClickListener(this);
		layout.findViewById(R.id.layout_recom).setOnClickListener(this);
		layout.findViewById(R.id.layout_recom2).setOnClickListener(this);
		layout.findViewById(R.id.layout_recom3).setOnClickListener(this);
		layout.findViewById(R.id.layout_recom4).setOnClickListener(this);
		layout.findViewById(R.id.layout_recom5).setOnClickListener(this);
		layout.findViewById(R.id.img_banner6).setOnClickListener(this);
		layout.findViewById(R.id.img_banner7).setOnClickListener(this);
		layout.findViewById(R.id.layout_special).setOnClickListener(this);
		layout.findViewById(R.id.layout_special2).setOnClickListener(this);
		layout.findViewById(R.id.layout_special3).setOnClickListener(this);
		layout.findViewById(R.id.layout_special4).setOnClickListener(this);
		layout.findViewById(R.id.img_banner8).setOnClickListener(this);
		layout.findViewById(R.id.img_banner9).setOnClickListener(this);
		mPtrScrollView = (PullToRefreshScrollView) layout
				.findViewById(R.id.ptrScrollView_home);
		mPtrScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						new GetDataTask().execute();
					}
				});

		mImgCover = (ImageView) layout.findViewById(R.id.img_cover);
		mImageView = (ImageView) layout.findViewById(R.id.img_indicator01);
		mScrollView = (HorizontalScrollView) layout
				.findViewById(R.id.layout_recom_banner);
		mScrollView2 = (HorizontalScrollView) layout
				.findViewById(R.id.layout_special_banner);
	}

	private void activeCategory() {
		MainActivity activity = (MainActivity) getActivity();
		activity.activeCategory();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// ��layout�Ӹ�������Ƴ�
		ViewGroup parent = (ViewGroup) layout.getParent();
		parent.removeView(layout);
	}

	private void initPager() {
		mPager = (ViewPager) layout.findViewById(R.id.pager_banner);
		FragmentManager fm = getChildFragmentManager();
		MyPagerAdapter adapter = new MyPagerAdapter(fm);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(1000);
		mPager.setOnPageChangeListener(new MyPagerListener());
	}

	/**
	 * �Զ�����
	 */
	private void autoScroll() {
		mPager.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!isDragging) {
					// ���û�û����ק�����Զ�����
					mPager.setCurrentItem(mPager.getCurrentItem() + 1);
				}
				if (isFoucusRight) {
					mScrollView.fullScroll(ScrollView.FOCUS_LEFT);
				} else {
					mScrollView.fullScroll(ScrollView.FOCUS_RIGHT);
				}
				isFoucusRight = !isFoucusRight;
				mPager.postDelayed(this, 3000);
			}
		}, 3000);
		mScrollView2.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (isFoucusRight) {
					mScrollView2.fullScroll(ScrollView.FOCUS_RIGHT);
				} else {
					mScrollView2.fullScroll(ScrollView.FOCUS_LEFT);
				}
				mScrollView2.postDelayed(this, 3000);
			}
		}, 4000);
	}

	/**
	 * ����ʱ
	 */
	private void initTimer() {
		final TextView tvHour = (TextView) layout.findViewById(R.id.tv_hour);
		final TextView tvMinute = (TextView) layout
				.findViewById(R.id.tv_minute);
		final TextView tvSecond = (TextView) layout
				.findViewById(R.id.tv_second);
		tvHour.post(new Runnable() {

			@Override
			public void run() {
				if (secondCount > 0) {

					secondCount--;
					int h = secondCount / 3600;
					int m = secondCount / 60 % 60;
					int s = secondCount % 60;
					StringBuffer hour = new StringBuffer();
					StringBuffer minute = new StringBuffer();
					StringBuffer second = new StringBuffer();
					if (h < 10) {
						hour.append(0);
					}
					if (m < 10) {
						minute.append(0);
					}
					if (s < 10) {
						second.append(0);
					}
					hour.append(h);
					minute.append(m);
					second.append(s);
					tvHour.setText(hour);
					tvMinute.setText(minute);
					tvSecond.setText(second);
					tvHour.postDelayed(this, 1000);
				}
			}
		});
	}

	class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			BannerItemFragment fragment = new BannerItemFragment();
			fragment.setResId(mBanner[position % mBanner.length]);
			fragment.setGoodsInfo(mInfos[position % mBanner.length]);
			return fragment;
		}

		@Override
		public int getCount() {
			return 10000;
		}

	}

	class MyPagerListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			int width = mImgCover.getWidth();
			LayoutParams layoutParams = (LayoutParams) mImageView
					.getLayoutParams();
			int rightMargin = layoutParams.rightMargin;
			int endPos = (width + rightMargin) * (position % 4);
			int startPos = 0;
			if (mLastPos < position) {
				// ͼƬ�����ƶ�
				startPos = (width + rightMargin) * (position % 4 - 1);
			} else {
				// ͼƬ�����ƶ�
				startPos = (width + rightMargin) * (position % 4 + 1);
			}
			ObjectAnimator.ofFloat(mImgCover, "translationX", startPos, endPos)
					.setDuration(300).start();
			mLastPos = position;
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				// �û���ק
				isDragging = true;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				// ����״̬
				isDragging = false;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				// ���ͷ�ʱ
				isDragging = false;
				break;

			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_home_category: // �л�������
			activeCategory();
			break;
		case R.id.img_home_search_code: // ��ά��ɨ��
			((MainActivity) getActivity()).scanQRCode();
			break;
		case R.id.layout_home_search:
			gotoSearch();
			break;
		case R.id.layout_my_focus: // �ҵĹ�ע
			startActivity(new Intent(getActivity(), FavorActivity.class));
			break;
		case R.id.layout_logistics: // ������ѯ
			startActivity(new Intent(getActivity(), OrdersActivity.class));
			break;
		case R.id.layout_top_up: // ��ֵ
			Intent intent = new Intent(getActivity(), WebActivity.class);
			intent.putExtra("direction", 1);
			startActivity(intent);
			break;
		case R.id.layout_film: // ��ӰƱ
			Intent intent2 = new Intent(getActivity(), WebActivity.class);
			intent2.putExtra("direction", 3);
			startActivity(intent2);
			break;
		case R.id.layout_game_top_up: // ��Ϸ��ֵ
			Intent intent3 = new Intent(getActivity(), WebActivity.class);
			intent3.putExtra("direction", 2);
			startActivity(intent3);
			break;
		case R.id.layout_purse: // С���
			startActivity(new Intent(getActivity(), PurseActivity.class));
			break;
		case R.id.layout_jingdou: // ��ȡ����
			Intent intent4 = new Intent(getActivity(), WebActivity.class);
			intent4.putExtra("direction", 4);
			startActivity(intent4);
			break;
		case R.id.layout_more: // ���
			startActivity(new Intent(getActivity(), BoxActivity.class));
			break;
		case R.id.layout_discount: // �ײ���ɱ
			gotoDetail(4);
			break;
		case R.id.layout_discount_phone: // �ֻ�ר��
			gotoDetail(5);
			break;
		case R.id.img_discount_banner: // �̷۷��Ĺ�
			gotoDetail(6);
			break;
		case R.id.layout_recom: // ֵ����
			gotoDetail(7);
			break;
		case R.id.layout_recom2: // ��ѡ�Ƽ�
			gotoDetail(8);
			break;
		case R.id.layout_recom3: // ����
			gotoDetail(9);
			break;
		case R.id.layout_recom4: // �Ź�
			gotoDetail(10);
			break;
		case R.id.layout_recom5: // �����ڳ�
			gotoDetail(11);
			break;
		case R.id.img_banner6:
			gotoDetail(12);
			break;
		case R.id.img_banner7:
			gotoDetail(13);
			break;
		case R.id.layout_special:	//Сʳ��
			gotoDetail(0);
			break;
		case R.id.layout_special2:	//�����
			gotoDetail(1);
			break;
		case R.id.layout_special3:	//���м�
			gotoDetail(2);
			break;
		case R.id.layout_special4:	//Ѫѹ��
			gotoDetail(3);
			break;
		case R.id.img_banner8:
			gotoDetail(13);
			break;
		case R.id.img_banner9:
			gotoDetail(14);
			break;

		default:
			break;
		}
	}
	
	/**
	 * ת����Ʒ����
	 */
	private void gotoDetail(int index) {
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra(Constants.INTENT_KEY.INFO_TO_DETAIL, mInfos[index]);
		startActivity(intent);
	}

	private void gotoSearch() {
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		startActivity(intent);
		// activity�����޶���
		getActivity().overridePendingTransition(0, 0);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		protected String[] doInBackground(Void... params) {
			// ����ˢ��
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		protected void onPostExecute(String[] result) {
			mPtrScrollView.onRefreshComplete();// �ر�ˢ�¶���
		}

	}

}
