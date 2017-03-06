package com.yajun.socketproject.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import com.example.market.R;
import com.example.market.bean.CategoryMenu.CategoryItem;
import com.example.market.bean.GoodsInfo;
import com.example.market.fragment.FilterMenuFragment;
import com.example.market.utils.Constants;
import com.example.market.utils.MyGridView;
import com.example.market.utils.NumberUtils;
import com.example.market.utils.MyGridView.OnGridScroll2TopListener;
import com.example.market.utils.MyListView;
import com.example.market.utils.MyListView.OnScroll2TopListener;
import com.lib.uil.ScreenUtils;
import com.lib.uil.UILUtils;
import com.nineoldandroids.animation.ObjectAnimator;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GoodsListActivity extends FragmentActivity implements
		OnClickListener {
	
	private ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
	private ArrayList<GoodsInfo> goodsListCopy = new ArrayList<GoodsInfo>();	//���ݣ����������ָ�

	private MenuDrawer mDrawer;
	private GoodsListAdapter mListAdapter;
	private boolean isGlobalMenuShow;
	private View mLayoutGlobalMenu;
	private ImageView mImgOverlay;
	private MyListView mListView;
	private View mOverlayHeader;
	private int mLastFirstPosition;
	private ImageView mImgGlobal;
	private ImageView mImgGlobalList;

	private int durationRotate = 700;// ��ת����ʱ��
	private int durationAlpha = 500;// ͸���ȶ���ʱ��
	// private int gridCode = -1;
	// private int listCode = -1;
	private MyGridView mGridView;
	private GoodsGridAdapter mGridAdapter;
	private ImageView mImgMenu;
	private ImageView mImgMenuList;

	private boolean isGrid; // �Ƿ�ΪGrid�б�
	private boolean isSortUp;	//�Ƿ�Ϊ�۸���������
	private ImageView mImgMenuGrid;
	private ImageView mImgGlobalGrid;
	private int menuSize;

	private FilterMenuFragment filterMenuFragment;
	private TextView mTvSaleVolume;
	private TextView mTvSaleVolumeList;
	private TextView mTvSaleVolumeGrid;
	private TextView mTvPrice;
	private TextView mTvPriceList;
	private TextView mTvPriceGrid;
	private ImageView mImgPriceGrid;
	private ImageView mImgPriceList;
	private ImageView mImgPrice;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initGoods();
		initMenu();
		initView();
		setOnListener();
		initListView();
		initGridView();
		mProgressBar.setVisibility(View.GONE);
	}

	private void initGoods() {
		goodsList.add(new GoodsInfo("100001", "Levi's��ά˹��ʿ����ʱ�г�������T��82176-0005 ��/�� L", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods01.jpg", "����Ь��", 153.00, "����96%", 1224, 1, 0));
		goodsList.add(new GoodsInfo("100002", "Levi's��ά˹505ϵ����ʿ����ֱ��ţ�п�00505-1185 ţ��ɫ 36 34", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods02.jpg", "����Ь��", 479.00, "����95%", 645, 0, 0));
		goodsList.add(new GoodsInfo("100003", "GXG��װ ����ר�� 2015��װ�¿� ��ʿʱ�а�ɫ����Բ�����T��#42244315 ��ɫ M", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods03.jpg", "����Ь��", 149.00, "��������", 1856, 0, 0));
		goodsList.add(new GoodsInfo("100004", "Apple iPad mini ME276CH/A �䱸 Retina ��ʾ�� 7.9Ӣ��ƽ����� ��16G WiFi�棩��ջ�ɫ", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods04.jpg", "��������", 2138.00, "����97%", 865, 0, 0));
		goodsList.add(new GoodsInfo("100005", "���루ThinkPad���ᱡϵ��E450C(008CD) 14Ӣ��ʼǱ����� ��i3-4005U 4GB 500G+8GSSD 1G WIN8.1��", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods05.jpg", "��������", 3299.00, "����95%", 236, 0, 0));
		goodsList.add(new GoodsInfo("100006", "�޼���Logitech��G502 ����Ӧ��Ϸ���", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods06.jpg", "����Ь��", 499.00, "����95%", 115, 0, 0));
		goodsList.add(new GoodsInfo("100007", "��ʿ��Swissgear��SA7777WH 12Ӣ��ʱ��������˫����Ա��� �װ�ɫ", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods07.jpg", "����Ь��", 199.00, "����95%", 745, 0, 0));
		goodsList.add(new GoodsInfo("100008", "����Transcend�� 340ϵ�� 256G SATA3 ��̬Ӳ��(TS256GSSD340)", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods08.jpg", "��������", 569.00, "����95%", 854, 1, 0));
		goodsList.add(new GoodsInfo("100009", "���ܣ�Canon�� EOS 700D �����׻� ��EF-S 18-135mm f/3.5-5.6 IS STM��ͷ��", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods09.jpg", "��������", 5099.00, "����94%", 991, 0, 0));
		goodsList.add(new GoodsInfo("100010", "��������F-WHEEL) ���ܵ綯���ֳ� ��ƽ����ֳ� ����ϵ������ ֧�� ���� ���� ��ɫD1��20KM��֧��", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods10.jpg", "�˶�����", 2999.00, "����93%", 1145, 0, 0));
		goodsList.add(new GoodsInfo("100011", "����21��26�����Ͻ����г� ����ŵ���� ����ɵ���������ɽ�س� QJ243 ��Ӫ", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods11.jpg", "�˶�����", 1088.00, "����92%", 909, 0, 0));
		goodsList.add(new GoodsInfo("100012", "���Ƕ�һ������������ ��Ӫ", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods12.jpg", "ͼ������", 25.40, "����95%", 1443, 0, 0));
		goodsList.add(new GoodsInfo("100013", "����Զ��", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods13.jpg", "ͼ������", 19.70, "����98%", 3702, 0, 0));
		goodsList.add(new GoodsInfo("100014", "���ڵ�����", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods14.jpg", "ͼ������", 38.40, "����97%", 442, 1, 0));
		goodsList.add(new GoodsInfo("100015", "Photoshopרҵ��ͼ���� �����1��", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods15.jpg", "ͼ������", 57.80, "����93%", 765, 0, 0));
		goodsListCopy.addAll(goodsList);
	}

	private void initMenu() {
		Intent intent = getIntent();
		CategoryItem categoryItem = (CategoryItem) intent
				.getSerializableExtra(Constants.INTENT_KEY.MENU_TO_GOODS_LIST);
		// gridCode = getIntent().getIntExtra("gridCode", -1);
		// listCode = getIntent().getIntExtra("listCode", -1);
		mDrawer = MenuDrawer
				.attach(this, MenuDrawer.Type.OVERLAY, Position.END);
		mDrawer.setMenuView(R.layout.menudrawer);
		mDrawer.setContentView(R.layout.activity_goods_list);
		// �˵�����Ӱ
		mDrawer.setDropShadowEnabled(false);
		filterMenuFragment = new FilterMenuFragment();
		// filterMenuFragment.setGoodsCode(listCode, gridCode);
		filterMenuFragment.setCategoryItem(categoryItem);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.menu_container, filterMenuFragment).commit();
	}

	/**
	 * ���ò˵����
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// ������menuSize��Ϊ��Ա�������ڴӵڶ���˵�����ʱ������ɲ˵���ʧ
		if (menuSize == 0) {
			int screenWidth = ScreenUtils.getScreenWidth(this);
			menuSize = screenWidth / 7 * 6;
			mDrawer.setMenuSize(menuSize);
		}
	}

	private void initGridView() {
		mGridView = (MyGridView) findViewById(R.id.gridView1);
		View head = getLayoutInflater().inflate(R.layout.head_goods_list, null);
		mGridView.addHeaderView(head, null, false);
		mGridAdapter = new GoodsGridAdapter();
		mGridView.setAdapter(mGridAdapter);
		mTvPriceGrid = (TextView) head.findViewById(R.id.tv_price);
		mTvSaleVolumeGrid = (TextView) head.findViewById(R.id.tv_salse_volume);
		mImgPriceGrid = (ImageView) head.findViewById(R.id.img_price);
		mImgGlobalGrid = (ImageView) head.findViewById(R.id.img_global);
		mImgMenuGrid = (ImageView) head.findViewById(R.id.img_category_menu);
		mImgMenuGrid.setOnClickListener(this);
		head.findViewById(R.id.btn_global).setOnClickListener(this);
		head.findViewById(R.id.btn_filter).setOnClickListener(this);
		head.findViewById(R.id.btn_price).setOnClickListener(this);
		head.findViewById(R.id.btn_salse_volume).setOnClickListener(this);
		head.findViewById(R.id.layout_category_search_bar).setOnClickListener(
				this);
		head.findViewById(R.id.img_back).setOnClickListener(this);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GoodsInfo info = goodsList.get(position - 2);
				gotoDetail(info);
			}
		});
		// ���������Ϸ�ʱ����mOverlayHeader
		mGridView.setOnGridScroll2TopListener(new OnGridScroll2TopListener() {

			public void scroll2Top() {
				mOverlayHeader.setVisibility(View.INVISIBLE);
			}
		});
		// ���Ϲ��������½ǳ��ֻص�������ť
		mGridView.setOnScrollListener(new OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem < mLastFirstPosition) {
					mOverlayHeader.setVisibility(View.VISIBLE);
				} else if (firstVisibleItem > mLastFirstPosition) {
					mOverlayHeader.setVisibility(View.INVISIBLE);
				}
				mLastFirstPosition = firstVisibleItem;

				if (firstVisibleItem > 0) {
					mImgOverlay.setVisibility(View.VISIBLE);
				} else {
					mImgOverlay.setVisibility(View.INVISIBLE);
				}
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

		});
	}

	private void setOnListener() {
		findViewById(R.id.img_back).setOnClickListener(this);
		findViewById(R.id.btn_global).setOnClickListener(this);
		findViewById(R.id.btn_filter).setOnClickListener(this);
		findViewById(R.id.btn_price).setOnClickListener(this);
		findViewById(R.id.btn_salse_volume).setOnClickListener(this);
		findViewById(R.id.layout_category_search_bar).setOnClickListener(this);
		mOverlayHeader.setOnClickListener(this);
		mLayoutGlobalMenu.setOnClickListener(this);
		mImgOverlay.setOnClickListener(this);
		mImgMenu.setOnClickListener(this);

	}

	private void initView() {
		mImgPrice = (ImageView) findViewById(R.id.img_price);
		mTvPrice = (TextView) findViewById(R.id.tv_price);
		mTvSaleVolume = (TextView) findViewById(R.id.tv_salse_volume);
		mLayoutGlobalMenu = findViewById(R.id.layout_global_menu);
		mOverlayHeader = findViewById(R.id.overlayHeader);
		mImgOverlay = (ImageView) findViewById(R.id.img_overlay);
		mImgGlobal = (ImageView) findViewById(R.id.img_global);
		mImgMenu = (ImageView) findViewById(R.id.img_category_menu);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

	}

	private void initListView() {
		mListView = (MyListView) findViewById(R.id.listView1);
		View head = getLayoutInflater().inflate(R.layout.head_goods_list, null);
		mListView.addHeaderView(head, null, false);
		mListAdapter = new GoodsListAdapter();
		mListView.setAdapter(mListAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GoodsInfo info = goodsList.get(position - 1);
				gotoDetail(info);
			}
		});
		// ���������Ϸ�ʱ����mOverlayHeader
		mListView.setOnScroll2TopListener(new OnScroll2TopListener() {

			@Override
			public void scroll2Top() {
				mOverlayHeader.setVisibility(View.INVISIBLE);
			}
		});
		// ���Ϲ��������½ǳ��ֻص�������ť
		mListView.setOnScrollListener(new OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem < mLastFirstPosition) {
					mOverlayHeader.setVisibility(View.VISIBLE);
				} else if (firstVisibleItem > mLastFirstPosition) {
					mOverlayHeader.setVisibility(View.INVISIBLE);
				}
				mLastFirstPosition = firstVisibleItem;

				if (firstVisibleItem > 0) {
					mImgOverlay.setVisibility(View.VISIBLE);
				} else {
					mImgOverlay.setVisibility(View.INVISIBLE);
				}
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

		});
		mImgPriceList = (ImageView) head.findViewById(R.id.img_price);
		mTvPriceList = (TextView) head.findViewById(R.id.tv_price);
		mTvSaleVolumeList = (TextView) head.findViewById(R.id.tv_salse_volume);
		mImgGlobalList = (ImageView) head.findViewById(R.id.img_global);
		mImgMenuList = (ImageView) head.findViewById(R.id.img_category_menu);
		mImgMenuList.setOnClickListener(this);
		head.findViewById(R.id.btn_global).setOnClickListener(this);
		head.findViewById(R.id.btn_filter).setOnClickListener(this);
		head.findViewById(R.id.btn_price).setOnClickListener(this);
		head.findViewById(R.id.btn_salse_volume).setOnClickListener(this);
		head.findViewById(R.id.layout_category_search_bar).setOnClickListener(
				this);
		head.findViewById(R.id.img_back).setOnClickListener(this);

	}
	
	/**
	 * ��Ʒ����
	 * @param info
	 */
	private void gotoDetail(GoodsInfo info) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(Constants.INTENT_KEY.INFO_TO_DETAIL, info);
		startActivity(intent);
	}
	
	/**
	 * �������˵���ѡ�������ø�һ���˵�
	 * @param position
	 * @param result
	 */
	public void setSelectedResult(String result) {
		filterMenuFragment.setSelectedResult(result);
	}

	class GoodsListAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = null;
			ViewHolder holder = null;
			if (convertView == null) {
				inflate = getLayoutInflater().inflate(
						R.layout.item_goods_list_list, null);
				holder = new ViewHolder();
				holder.imgIcon = (ImageView) inflate.findViewById(R.id.img_icon);
				holder.imgVip = (ImageView) inflate.findViewById(R.id.img_icon_vip);
				holder.tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
				holder.tvPrice = (TextView) inflate.findViewById(R.id.tv_price);
				holder.tvPercent = (TextView) inflate.findViewById(R.id.tv_percent);
				holder.tvNum = (TextView) inflate.findViewById(R.id.tv_num);
				inflate.setTag(holder);
			} else {
				inflate = convertView;
				holder = (ViewHolder) inflate.getTag();
			}
			GoodsInfo goodsInfo = goodsList.get(position);
			holder.tvTitle.setText(goodsInfo.getGoodsName());
			holder.tvPrice.setText(NumberUtils.formatPrice(goodsInfo.getGoodsPrice()));
			holder.tvPercent.setText(goodsInfo.getGoodsPercent());
			holder.tvNum.setText(goodsInfo.getGoodsComment() + "��");
			UILUtils.displayImage(GoodsListActivity.this, goodsInfo.getGoodsIcon(), holder.imgIcon);
			if (goodsInfo.getIsPhone() == 1) {
				holder.imgVip.setVisibility(View.VISIBLE);
			} else {
				holder.imgVip.setVisibility(View.INVISIBLE);
			}
			return inflate;
		}

		@Override
		public int getCount() {
			return goodsList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}

	class GoodsGridAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = null;
			ViewHolder holder = null;
			if (convertView == null) {
				inflate = getLayoutInflater().inflate(
						R.layout.item_goods_list_grid, null);
				holder = new ViewHolder();
				holder.imgIcon = (ImageView) inflate.findViewById(R.id.img_icon);
				holder.tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
				holder.tvPrice = (TextView) inflate.findViewById(R.id.tv_price);
				inflate.setTag(holder);
			} else {
				inflate = convertView;
				holder = (ViewHolder) inflate.getTag();
			}
			GoodsInfo goodsInfo = goodsList.get(position);
			holder.tvTitle.setText(goodsInfo.getGoodsName());
			holder.tvPrice.setText(NumberUtils.formatPrice(goodsInfo.getGoodsPrice()));
			UILUtils.displayImage(GoodsListActivity.this, goodsInfo.getGoodsIcon(), holder.imgIcon);
			return inflate;
		}

		@Override
		public int getCount() {
			return goodsList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_category_menu: // ��ͼ�л��˵�
			changeGrid();
			break;
		case R.id.btn_global: // �ۺ�
			showGlobalMenu();
			break;
		case R.id.layout_global_menu: // �ۺ������˵�
			showGlobalMenu();
			break;
		case R.id.img_overlay:
			mListView.setSelection(0);
			mGridView.setSelection(0);
			break;
		case R.id.btn_filter: // ɸѡ
			toggleFilterMenu();
			break;
		case R.id.btn_price: // �۸�����
			sortByPrice();
			break;
		case R.id.btn_salse_volume: // ��������
			sortByVolume();
			break;
		case R.id.layout_category_search_bar: // ����
			gotoSearch();
			break;
		case R.id.img_back: // ����
			finish();
			break;
		case R.id.overlayHeader:
			break;

		default:
			break;
		}
	}
	
	/**
	 * ����
	 */
	private void sortByPrice() {
		mProgressBar.setVisibility(View.VISIBLE);
		isSortUp = !isSortUp;
		if (isSortUp) {
			sortUp();
		} else {
			sortDown();
		}
	}
	
	/**
	 * ���۸�����
	 */
	private void sortUp() {
		int darkgray = getResources().getColor(R.color.darkgray);
		mTvSaleVolume.setTextColor(darkgray);
		mTvSaleVolumeList.setTextColor(darkgray);
		mTvSaleVolumeGrid.setTextColor(darkgray);
		mTvPrice.setTextColor(Color.RED);
		mTvPriceList.setTextColor(Color.RED);
		mTvPriceGrid.setTextColor(Color.RED);
		mImgPrice.setImageResource(R.drawable.sort_button_price_up);
		mImgPriceList.setImageResource(R.drawable.sort_button_price_up);
		mImgPriceGrid.setImageResource(R.drawable.sort_button_price_up);
		Collections.sort(goodsList, new Comparator<GoodsInfo>() {

			@Override
			public int compare(GoodsInfo lhs, GoodsInfo rhs) {
				return Double.compare(lhs.getGoodsPrice(), rhs.getGoodsPrice());
			}
		});
		mListAdapter.notifyDataSetChanged();
		mGridAdapter.notifyDataSetChanged();
		mProgressBar.setVisibility(View.GONE);
	}
	
	/**
	 * ���۸���
	 */
	private void sortDown() {
		mImgPrice.setImageResource(R.drawable.sort_button_price_down);
		mImgPriceList.setImageResource(R.drawable.sort_button_price_down);
		mImgPriceGrid.setImageResource(R.drawable.sort_button_price_down);
		Collections.sort(goodsList, new Comparator<GoodsInfo>() {
			
			@Override
			public int compare(GoodsInfo lhs, GoodsInfo rhs) {
				return Double.compare(rhs.getGoodsPrice(), lhs.getGoodsPrice());
			}
		});
		mListAdapter.notifyDataSetChanged();
		mGridAdapter.notifyDataSetChanged();
		mProgressBar.setVisibility(View.GONE);
	}
	
	/**
	 * �������򣺻�ԭ����
	 */
	private void sortByVolume() {
		mProgressBar.setVisibility(View.VISIBLE);
		isSortUp = false;
		int darkgray = getResources().getColor(R.color.darkgray);
		mTvSaleVolume.setTextColor(Color.RED);
		mTvSaleVolumeList.setTextColor(Color.RED);
		mTvSaleVolumeGrid.setTextColor(Color.RED);
		mTvPrice.setTextColor(darkgray);
		mTvPriceList.setTextColor(darkgray);
		mTvPriceGrid.setTextColor(darkgray);
		mImgPrice.setImageResource(R.drawable.sort_button_price);
		mImgPriceList.setImageResource(R.drawable.sort_button_price);
		mImgPriceGrid.setImageResource(R.drawable.sort_button_price);
		goodsList.clear();
		goodsList.addAll(goodsListCopy);
		mListAdapter.notifyDataSetChanged();
		mGridAdapter.notifyDataSetChanged();
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * �л���ͼ
	 */
	private void changeGrid() {
		isGrid = !isGrid;
		if (isGrid) {
			mGridView.setSelection(mListView.getFirstVisiblePosition());
			mListView.setVisibility(View.INVISIBLE);
			mGridView.setVisibility(View.VISIBLE);
			mImgMenu.setImageResource(R.drawable.product_list_top_list_normal);
			mImgMenuList
					.setImageResource(R.drawable.product_list_top_list_normal);
			mImgMenuGrid
					.setImageResource(R.drawable.product_list_top_list_normal);
		} else {
			mListView.setSelection(mGridView.getFirstVisiblePosition());
			mGridView.setVisibility(View.INVISIBLE);
			mListView.setVisibility(View.VISIBLE);
			mImgMenu.setImageResource(R.drawable.product_list_top_grid_normal);
			mImgMenuList
					.setImageResource(R.drawable.product_list_top_grid_normal);
			mImgMenuGrid
					.setImageResource(R.drawable.product_list_top_grid_normal);
		}
	}

	public void toggleFilterMenu() {
		mDrawer.toggleMenu();
	}

	private void gotoSearch() {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
		// activity�����޶���
		overridePendingTransition(0, 0);
	}

	/**
	 * ��ʾ�ۺϲ˵���ͼ�궯��
	 */
	private void showGlobalMenu() {
		isGlobalMenuShow = !isGlobalMenuShow;
		if (isGlobalMenuShow) {
			ObjectAnimator.ofFloat(mImgGlobal, "rotation", 0, 180)
					.setDuration(durationRotate).start();
			ObjectAnimator.ofFloat(mImgGlobalList, "rotation", 0, 180)
					.setDuration(durationRotate).start();
			ObjectAnimator.ofFloat(mImgGlobalGrid, "rotation", 0, 180)
					.setDuration(durationRotate).start();
			mLayoutGlobalMenu.setVisibility(View.VISIBLE);
			ObjectAnimator.ofFloat(mLayoutGlobalMenu, "alpha", 0, 1)
					.setDuration(durationAlpha).start();
		} else {
			ObjectAnimator.ofFloat(mImgGlobal, "rotation", 180, 360)
					.setDuration(durationRotate).start();
			ObjectAnimator.ofFloat(mImgGlobalList, "rotation", 180, 360)
					.setDuration(durationRotate).start();
			ObjectAnimator.ofFloat(mImgGlobalGrid, "rotation", 180, 360)
					.setDuration(durationRotate).start();
			ObjectAnimator.ofFloat(mLayoutGlobalMenu, "alpha", 1, 0)
					.setDuration(durationAlpha).start();
			mLayoutGlobalMenu.postDelayed(new Runnable() {

				@Override
				public void run() {
					mLayoutGlobalMenu.setVisibility(View.INVISIBLE);
				}
			}, durationAlpha);
		}

	}

	/**
	 * �������ʱ���ȹرղ˵�
	 */
	@Override
	public void onBackPressed() {
		// TODO
		// ��ȡ��ǰջ�е�Ƭ����
		FragmentManager fm = getSupportFragmentManager();
		int count = fm.getBackStackEntryCount();
		if (count == 0) {
			final int drawerState = mDrawer.getDrawerState();
			if (drawerState == MenuDrawer.STATE_OPEN
					|| drawerState == MenuDrawer.STATE_OPENING) {
				mDrawer.closeMenu();
				return;
			}
		}
		super.onBackPressed();
	}

	class ViewHolder {
		ImageView imgIcon;
		ImageView imgVip;
		TextView tvTitle;
		TextView tvPrice;
		TextView tvPercent;
		TextView tvNum;
	}
}
