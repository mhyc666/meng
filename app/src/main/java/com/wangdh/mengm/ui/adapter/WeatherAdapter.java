package com.wangdh.mengm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangdh.mengm.R;
import com.wangdh.mengm.bean.WeatherAllData;

import java.util.List;

import butterknife.BindView;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private static final int TYPE_THREE = 2;
    private static final int TYPE_FORE = 3;
    private static final int TYPE_FIVE = 4;
    private WeatherAllData data;

    public WeatherAdapter(WeatherAllData weatherData) {
        this.data = weatherData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == WeatherAdapter.TYPE_ONE) {
            return WeatherAdapter.TYPE_ONE;
        }
        if (position == WeatherAdapter.TYPE_TWO) {
            return WeatherAdapter.TYPE_TWO;
        }
        if (position == WeatherAdapter.TYPE_THREE) {
            return WeatherAdapter.TYPE_THREE;
        }
        if (position == WeatherAdapter.TYPE_FORE) {
            return WeatherAdapter.TYPE_FORE;
        }
        if (position == WeatherAdapter.TYPE_FIVE) {
            return WeatherAdapter.TYPE_FIVE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ONE:
                return new NowWeatherViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_weather_one, parent, false));
            case TYPE_TWO:
                return new HoursWeatherViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_weather_two, parent, false));
            case TYPE_THREE:
                return new FutureViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_weather_three, parent, false));
            case TYPE_FORE:
                return new TodayViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_weather_fore, parent, false));
            case TYPE_FIVE:
                return new SuggestionViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_weather_five, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                ((NowWeatherViewHolder) holder).bind(data);
                break;
            case TYPE_TWO:
                ((HoursWeatherViewHolder) holder).bind(data);
                break;
            case TYPE_THREE:
                ((FutureViewHolder) holder).bind(data);
                break;
            case TYPE_FORE:
                ((TodayViewHolder) holder).bind(data);
                break;
            case TYPE_FIVE:
                ((SuggestionViewHolder) holder).bind(data);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? 5 : 0;
    }


    class NowWeatherViewHolder extends BaseViewHolder<WeatherAllData> {
        @BindView(R.id.tv_wd)
        TextView mTv;
        @BindView(R.id.tv_q)
        TextView mTq;
        @BindView(R.id.tv_zl)
        TextView mTzl;
        @BindView(R.id.temp_max)
        TextView Tmax;
        @BindView(R.id.temp_min)
        TextView Tmin;
        @BindView(R.id.iv)
        ImageView mIv;

        NowWeatherViewHolder(View view) {
            super(view);
        }

        @Override
        protected void bind(WeatherAllData weather) {
            try {
                mTv.setText(String.format("%s℃", weather.getHeWeather5().get(0).getNow().getTmp()));
                mTq.setText(weather.getHeWeather5().get(0).getNow().getCond().getTxt());
                mTzl.setText(weather.getHeWeather5().get(0).getAqi().getCity().getQlty());
                Tmax.setText(String.format("↑%s℃", weather.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp().getMax()));
                Tmin.setText(String.format("↓%s℃", weather.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp().getMin()));
                int code = weather.getHeWeather5().get(0).getNow().getCond().getCode();
                mIv.setImageResource(image(code));
            } catch (Exception e) {
                Log.i("toast", e.getMessage());
            }
        }
    }

    class FutureViewHolder extends BaseViewHolder<WeatherAllData> {
        @BindView(R.id.t1)
        TextView t1;
        @BindView(R.id.i1)
        ImageView i1;
        @BindView(R.id.wd1)
        TextView wd1;
        @BindView(R.id.t2)
        TextView t2;
        @BindView(R.id.i2)
        ImageView i2;
        @BindView(R.id.wd2)
        TextView wd2;
        @BindView(R.id.t3)
        TextView t3;
        @BindView(R.id.i3)
        ImageView i3;
        @BindView(R.id.wd3)
        TextView wd3;
        @BindView(R.id.tq1)
        TextView tq1;
        @BindView(R.id.tq2)
        TextView tq2;
        @BindView(R.id.tq3)
        TextView tq3;

        public FutureViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(WeatherAllData weatherAllData) {
            List<WeatherAllData.HeWeather5Bean.DailyForecastBean> data = weatherAllData.getHeWeather5().get(0).getDaily_forecast();
            t1.setText(String.format("今天/%s", data.get(0).getDate().substring(data.get(0).getDate().length() - 5)));
            int code = data.get(0).getCond().getCode_d();
            i1.setImageResource(image(code));
            tq1.setText(data.get(0).getCond().getTxt_d() + "/" + data.get(0).getWind().getDir());
            wd1.setText(data.get(0).getTmp().getMax() + "℃～" + data.get(0).getTmp().getMin() + "℃");

            t2.setText(String.format("明天/%s", data.get(1).getDate().substring(data.get(1).getDate().length() - 5)));
            int code2 = data.get(1).getCond().getCode_d();
            i2.setImageResource(image(code2));
            tq2.setText(data.get(1).getCond().getTxt_d() + "/" + data.get(1).getWind().getDir());
            wd2.setText(data.get(1).getTmp().getMax() + "℃～" + data.get(1).getTmp().getMin() + "℃");

            t3.setText(String.format("后天/%s", data.get(2).getDate().substring(data.get(2).getDate().length() - 5)));
            int code3 = data.get(2).getCond().getCode_d();
            i3.setImageResource(image(code3));
            tq3.setText(data.get(2).getCond().getTxt_d() + "/" + data.get(2).getWind().getDir());
            wd3.setText(data.get(2).getTmp().getMax() + "℃～" + data.get(2).getTmp().getMin() + "℃");
        }
    }


    class SuggestionViewHolder extends BaseViewHolder<WeatherAllData> {

        @BindView(R.id.image1)
        ImageView image1;
        @BindView(R.id.tv_t1)
        TextView tvT1;
        @BindView(R.id.tv_txt1)
        TextView tvTxt1;
        @BindView(R.id.image2)
        ImageView image2;
        @BindView(R.id.tv_t2)
        TextView tvT2;
        @BindView(R.id.tv_txt2)
        TextView tvTxt2;
        @BindView(R.id.image3)
        ImageView image3;
        @BindView(R.id.tv_t3)
        TextView tvT3;
        @BindView(R.id.tv_txt3)
        TextView tvTxt3;
        @BindView(R.id.image4)
        ImageView image4;
        @BindView(R.id.tv_t4)
        TextView tvT4;
        @BindView(R.id.tv_txt4)
        TextView tvTxt4;
        @BindView(R.id.image5)
        ImageView image5;
        @BindView(R.id.tv_t5)
        TextView tvT5;
        @BindView(R.id.tv_txt5)
        TextView tvTxt5;
        @BindView(R.id.image6)
        ImageView image6;
        @BindView(R.id.tv_t6)
        TextView tvT6;
        @BindView(R.id.tv_txt6)
        TextView tvTxt6;
        @BindView(R.id.image7)
        ImageView image7;
        @BindView(R.id.tv_t7)
        TextView tvT7;
        @BindView(R.id.tv_txt7)
        TextView tvTxt7;
        @BindView(R.id.image8)
        ImageView image8;
        @BindView(R.id.tv_t8)
        TextView tvT8;
        @BindView(R.id.tv_txt8)
        TextView tvTxt8;

        SuggestionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(WeatherAllData weatherAllData) {
            try {
                WeatherAllData.HeWeather5Bean.SuggestionBean data = weatherAllData.getHeWeather5().get(0).getSuggestion();

                tvT1.setText(String.format("空气指数─%s", data.getAir().getBrf()));
                tvTxt1.setText(data.getAir().getTxt());
                image1.setImageResource(R.mipmap.s_kq);


                tvT2.setText(String.format("舒适度指数─%s", data.getComf().getBrf()));
                tvTxt2.setText(data.getComf().getTxt());
                image2.setImageResource(R.mipmap.s_ssd);


                tvT3.setText(String.format("洗车指数─%s", data.getCw().getBrf()));
                tvTxt3.setText(data.getCw().getTxt());
                image3.setImageResource(R.mipmap.s_xc);

                tvT4.setText(String.format("穿衣指数─%s", data.getDrsg().getBrf()));
                tvTxt4.setText(data.getDrsg().getTxt());
                image4.setImageResource(R.mipmap.s_cy);

                tvT5.setText(String.format("感冒指数─%s", data.getFlu().getBrf()));
                tvTxt5.setText(data.getFlu().getTxt());
                image5.setImageResource(R.mipmap.s_gm);

                tvT6.setText(String.format("运动指数─%s", data.getSport().getBrf()));
                tvTxt6.setText(data.getSport().getTxt());
                image6.setImageResource(R.mipmap.s_yd);

                tvT7.setText(String.format("旅游指数─%s", data.getTrav().getBrf()));
                tvTxt7.setText(data.getTrav().getTxt());
                image7.setImageResource(R.mipmap.s_ly);

                tvT8.setText(String.format("紫外线指数─%s", data.getUv().getBrf()));
                tvTxt8.setText(data.getUv().getTxt());
                image8.setImageResource(R.mipmap.s_zwx);
            } catch (Exception e) {
                e.getMessage();
            }

        }
    }


    class TodayViewHolder extends BaseViewHolder<WeatherAllData> {
        @BindView(R.id.tv_o1)
        TextView tvO1;
        @BindView(R.id.tv_o2)
        TextView tvO2;
        @BindView(R.id.tv_o3)
        TextView tvO3;
        @BindView(R.id.tv_o4)
        TextView tvO4;
        @BindView(R.id.tv_o5)
        TextView tvO5;
        @BindView(R.id.tv_o6)
        TextView tvO6;
        @BindView(R.id.tv_t1)
        TextView tvT1;
        @BindView(R.id.tv_t2)
        TextView tvT2;
        @BindView(R.id.tv_t3)
        TextView tvT3;
        @BindView(R.id.tv_t4)
        TextView tvT4;
        @BindView(R.id.tv_t5)
        TextView tvT5;
        @BindView(R.id.tv_t6)
        TextView tvT6;
        @BindView(R.id.tv_s1)
        TextView tvS1;
        @BindView(R.id.tv_s2)
        TextView tvS2;
        @BindView(R.id.tv_s3)
        TextView tvS3;
        @BindView(R.id.tv_s4)
        TextView tvS4;
        @BindView(R.id.tv_s5)
        TextView tvS5;
        @BindView(R.id.tv_s6)
        TextView tvS6;

        TodayViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(WeatherAllData weatherAllData) {
            try {
                tvO1.setText(data.getHeWeather5().get(0).getNow().getWind().getDir());
                tvO2.setText(data.getHeWeather5().get(0).getNow().getWind().getSc());
                tvO3.setText("空气湿度");
                tvO4.setText(data.getHeWeather5().get(0).getNow().getHum());
                tvO5.setText("体感温度");
                tvO6.setText(data.getHeWeather5().get(0).getNow().getFl());
                tvT1.setText("空气质量");
                tvT2.setText(data.getHeWeather5().get(0).getAqi().getCity().getPm10() + " " + data.getHeWeather5().get(0).getAqi().getCity().getQlty());
                tvT3.setText("气体压强");
                tvT4.setText(String.format("%shPa", data.getHeWeather5().get(0).getNow().getPres()));
                tvT5.setText("紫外线");
                tvT6.setText(data.getHeWeather5().get(0).getDaily_forecast().get(0).getUv());
                tvS1.setText("白天天气");
                tvS2.setText(data.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getTxt_d());
                tvS3.setText("夜间天气");
                tvS4.setText(data.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getTxt_n());
                tvS5.setText("降水率");
                tvS6.setText(data.getHeWeather5().get(0).getDaily_forecast().get(0).getPop());
            } catch (Exception e) {
                Log.i("toast", e.getMessage());
            }
        }
    }

    class HoursWeatherViewHolder extends BaseViewHolder<WeatherAllData> {
        @BindView(R.id.tv_time1)
        TextView mT1;
        @BindView(R.id.iv_w1)
        ImageView mIv1;
        @BindView(R.id.tv_tq1)
        TextView mTq1;
        @BindView(R.id.tv_f1)
        TextView mTf1;
        @BindView(R.id.tv_time2)
        TextView mT2;
        @BindView(R.id.iv_w2)
        ImageView mIv2;
        @BindView(R.id.tv_tq2)
        TextView mTq2;
        @BindView(R.id.tv_f2)
        TextView mTf2;
        @BindView(R.id.tv_time3)
        TextView mT3;
        @BindView(R.id.iv_w3)
        ImageView mIv3;
        @BindView(R.id.tv_tq3)
        TextView mTq3;
        @BindView(R.id.tv_f3)
        TextView mTf3;
        @BindView(R.id.tv_time4)
        TextView mT4;
        @BindView(R.id.iv_w4)
        ImageView mIv4;
        @BindView(R.id.tv_tq4)
        TextView mTq4;
        @BindView(R.id.tv_f4)
        TextView mTf4;
        @BindView(R.id.tv_time5)
        TextView mT5;
        @BindView(R.id.iv_w5)
        ImageView mIv5;
        @BindView(R.id.tv_tq5)
        TextView mTq5;
        @BindView(R.id.tv_f5)
        TextView mTf5;
        @BindView(R.id.tv_wd1)
        TextView mTwd1;
        @BindView(R.id.tv_wd2)
        TextView mTwd2;
        @BindView(R.id.tv_wd3)
        TextView mTwd3;
        @BindView(R.id.tv_wd4)
        TextView mTwd4;
        @BindView(R.id.tv_wd5)
        TextView mTwd5;
        @BindView(R.id.ll1)
        LinearLayout mLl1;
        @BindView(R.id.ll2)
        LinearLayout mLl2;
        @BindView(R.id.ll3)
        LinearLayout mLl3;
        @BindView(R.id.ll4)
        LinearLayout mLl4;
        @BindView(R.id.ll5)
        LinearLayout mLl5;
        @BindView(R.id.no_data)
        TextView t;

        HoursWeatherViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(WeatherAllData weatherAllData) {   //此处可用RecyclerView
            try {
                List<WeatherAllData.HeWeather5Bean.HourlyForecastBean> data = weatherAllData.getHeWeather5().get(0).getHourly_forecast();
                if (data.size() == 0) {
                    t.setVisibility(View.VISIBLE);
                    mLl1.setVisibility(View.GONE);
                    mLl2.setVisibility(View.GONE);
                    mLl3.setVisibility(View.GONE);
                    mLl4.setVisibility(View.GONE);
                    mLl5.setVisibility(View.GONE);
                } else if (data.size() == 1) {
                    mLl2.setVisibility(View.GONE);
                    mLl3.setVisibility(View.GONE);
                    mLl4.setVisibility(View.GONE);
                    mLl5.setVisibility(View.GONE);
                } else if (data.size() == 2) {
                    mLl3.setVisibility(View.GONE);
                    mLl4.setVisibility(View.GONE);
                    mLl5.setVisibility(View.GONE);
                } else if (data.size() == 3) {
                    mLl4.setVisibility(View.GONE);
                    mLl5.setVisibility(View.GONE);
                } else if (data.size() == 4) {
                    mLl5.setVisibility(View.GONE);
                }
                String s = data.get(0).getDate().substring(data.get(0).getDate().length() - 5);
                mT1.setText(s);
                int code1 = data.get(0).getCond().getCode();
                mIv1.setImageResource(image(code1));
                mTq1.setText(data.get(0).getCond().getTxt());
                mTf1.setText(data.get(0).getWind().getDir());
                mTwd1.setText(String.format("%s℃", data.get(0).getTmp()));

                String s2 = data.get(1).getDate().substring(data.get(1).getDate().length() - 5);
                mT2.setText(s2);
                int code2 = data.get(1).getCond().getCode();
                mIv2.setImageResource(image(code2));
                mTq2.setText(data.get(1).getCond().getTxt());
                mTf2.setText(data.get(1).getWind().getDir());
                mTwd2.setText(String.format("%s℃", data.get(1).getTmp()));

                String s3 = data.get(2).getDate().substring(data.get(1).getDate().length() - 5);
                mT3.setText(s3);
                int code3 = data.get(2).getCond().getCode();
                mIv3.setImageResource(image(code3));
                mTq3.setText(data.get(2).getCond().getTxt());
                mTf3.setText(data.get(2).getWind().getDir());
                mTwd3.setText(String.format("%s℃", data.get(2).getTmp()));

                String s4 = data.get(3).getDate().substring(data.get(3).getDate().length() - 5);
                mT4.setText(s4);
                int code4 = data.get(3).getCond().getCode();
                mIv4.setImageResource(image(code4));
                mTq4.setText(data.get(3).getCond().getTxt());
                mTf4.setText(data.get(3).getWind().getDir());
                mTwd4.setText(String.format("%s℃", data.get(3).getTmp()));

                String s5 = data.get(4).getDate().substring(data.get(4).getDate().length() - 5);
                mT5.setText(s5);
                int code5 = data.get(4).getCond().getCode();
                mIv5.setImageResource(image(code5));
                mTq5.setText(data.get(4).getCond().getTxt());
                mTf5.setText(data.get(4).getWind().getDir());
                mTwd5.setText(String.format("%s℃", data.get(4).getTmp()));
            } catch (Exception e) {
                Log.i("toast", e.getMessage());
            }
        }
    }


    private int image(int code) {
        if (code == 100) {
            return R.mipmap.w100;
        }
        if (code == 101) {
            return R.mipmap.w101;
        }
        if (code == 102) {
            return R.mipmap.w102;
        }
        if (code == 103) {
            return R.mipmap.w103;
        }
        if (code == 104) {
            return R.mipmap.w104;
        }
        if (code == 200) {
            return R.mipmap.w200;
        }
        if (code == 201) {
            return R.mipmap.w201;
        }
        if (code == 202) {
            return R.mipmap.w202;
        }
        if (code == 203) {
            return R.mipmap.w203;
        }
        if (code == 204) {
            return R.mipmap.w204;
        }

        if (code == 205) {
            return R.mipmap.w205;
        }
        if (code == 206) {
            return R.mipmap.w206;
        }
        if (code == 207) {
            return R.mipmap.w207;
        }
        if (code == 208) {
            return R.mipmap.w208;
        }
        if (code == 209) {
            return R.mipmap.w209;
        }
        if (code == 210) {
            return R.mipmap.w210;
        }
        if (code == 211) {
            return R.mipmap.w211;
        }
        if (code == 212) {
            return R.mipmap.w212;
        }
        if (code == 213) {
            return R.mipmap.w213;
        }
        if (code == 300) {
            return R.mipmap.w300;
        }

        if (code == 301) {
            return R.mipmap.w301;
        }
        if (code == 302) {
            return R.mipmap.w302;
        }
        if (code == 303) {
            return R.mipmap.w303;
        }
        if (code == 304) {
            return R.mipmap.w304;
        }
        if (code == 305) {
            return R.mipmap.w305;
        }
        if (code == 306) {
            return R.mipmap.w306;
        }
        if (code == 307) {
            return R.mipmap.w307;
        }
        if (code == 308) {
            return R.mipmap.w308;
        }
        if (code == 309) {
            return R.mipmap.w309;
        }
        if (code == 310) {
            return R.mipmap.w310;
        }

        if (code == 311) {
            return R.mipmap.w311;
        }
        if (code == 312) {
            return R.mipmap.w312;
        }
        if (code == 313) {
            return R.mipmap.w313;
        }
        if (code == 400) {
            return R.mipmap.w400;
        }
        if (code == 401) {
            return R.mipmap.w401;
        }
        if (code == 402) {
            return R.mipmap.w402;
        }
        if (code == 403) {
            return R.mipmap.w403;
        }
        if (code == 404) {
            return R.mipmap.w404;
        }
        if (code == 405) {
            return R.mipmap.w405;
        }
        if (code == 406) {
            return R.mipmap.w406;
        }

        if (code == 407) {
            return R.mipmap.w407;
        }
        if (code == 500) {
            return R.mipmap.w500;
        }
        if (code == 501) {
            return R.mipmap.w501;
        }
        if (code == 502) {
            return R.mipmap.w502;
        }
        if (code == 503) {
            return R.mipmap.w503;
        }
        if (code == 504) {
            return R.mipmap.w504;
        }
        if (code == 507) {
            return R.mipmap.w507;
        }
        if (code == 508) {
            return R.mipmap.w508;
        }
        if (code == 900) {
            return R.mipmap.w900;
        }
        if (code == 901) {
            return R.mipmap.w901;
        }
        return R.mipmap.w999;
    }
}

