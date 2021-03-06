package com.osj.stockinfomation.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.CommonCallback.NsPredicate;
import com.osj.stockinfomation.CommonCallback.RecyclerViewClickListener;
import com.osj.stockinfomation.util.ErrorController;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by SJH, NomadSoft.Inc on 2017-10-16.
 */

public abstract class NsBaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    protected Context mContext;

    protected List<T> data;

    protected RecyclerViewClickListener<T> mClickListener;

    protected CommonCallback.SingleObjectActionCallback<Integer> mNotifyCallback;

    public NsBaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public NsBaseRecyclerViewAdapter(Context mContext, List<T> data) {
        this.mContext = mContext;
        this.data = data;
    }



    public NsBaseRecyclerViewAdapter(Context mContext, List<T> data, RecyclerViewClickListener<T> mClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.mClickListener = mClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void addAll(List<T> list) {

        try {

            int position = data.size();

            data.addAll(list);

            nsNotifyItemRangeInserted(position, list.size());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void addAll2(List<T> list) {

        try {


            for (T t : list) {

                data.add(t);

            }
            nsNotifyDataSetChanged();


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void removeItem(T item, NsPredicate<T> predicate) {

        try {

            removeItemPredicate(this, getData(), predicate);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parents, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull VH holder, int position);

    /**
     * ??????????????? ?????? ???????????? ?????? ????????????.
     *
     * @param adapter
     * @param list
     * @param predicate
     * @param <T>
     */
    public static <T> void removeItemPredicate(RecyclerView.Adapter adapter, List<T> list, NsPredicate<T> predicate) {

        try {

            int position = -1;

            if (list == null) {
                return;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    position = list.indexOf(t);
                    break;
                }
            }

            if (position != -1) {
                list.remove(position);

                if (adapter instanceof NsBaseRecyclerViewAdapter) {

                    ((NsBaseRecyclerViewAdapter) adapter).nsNotifyItemRemoved(position);

                } else {

                    adapter.notifyItemRemoved(position);
                }

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void replaceAll(List<T> data) {

        this.data = data;
        nsNotifyDataSetChanged();
    }

    public RecyclerViewClickListener<T> getClickListener() {
        return mClickListener;
    }

    public void setClickListener(RecyclerViewClickListener<T> mClickListener) {
        this.mClickListener = mClickListener;
    }

    /**
     * ????????? ???????????? ?????? ???????????? ????????????. ???, ????????? ?????? ????????? ???????????? ?????????.
     *
     * @param item
     */
    public void addWithoutDuplicate(T item, NsPredicate<T> predicate) {

        try {

            boolean isExist = isItemExist(getData(), predicate);

            if (!isExist) {

                data.add(item);
                nsNotifyItemInserted(data.indexOf(item));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static <T> boolean isItemExist(List<T> list, NsPredicate<T> predicate) {

        try {

            if (list == null) {
                list = new ArrayList<>();
                return false;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    return true;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    /**
     * ?????? position??? ???????????? ????????? ???????????? ????????????.
     *
     * @param position
     * @return
     */
    protected boolean isLastOfList(int position) {

        try {

            if (data != null && data.size() > 0) {

                if (position == data.size() - 1) {

                    return true;

                }
            } else {//????????? ????????? 0?????? ????????? ???????????????.

                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public void addItem(T item) {

        try {

            if (data == null) {
                data = new ArrayList<>();
            }

            data.add(item);
            nsNotifyItemInserted(data.indexOf(item));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setNotifyCallback(CommonCallback.SingleObjectActionCallback<Integer> mNotifyCallback) {
        this.mNotifyCallback = mNotifyCallback;
    }


    public void nsNotifyDataSetChanged() {

        try {

            notifyDataSetChanged();

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemInserted(int position) {

        try {

            notifyItemInserted(position);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemRemoved(int position) {

        try {

            notifyItemRemoved(position);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemChanged(int position) {

        try {

            notifyItemChanged(position);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemRangeInserted(int start, int size) {

        try {

            notifyItemRangeInserted(start, size);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void addInPosition(int position, T item) {

        try {

            if (data == null) {

                data = new ArrayList<>();
            }

            data.add(position, item);
            notifyItemInserted(position);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void addAll(int position, List<T> list) {

        try {

            if (data == null) {

                data = new ArrayList<>();
            }

            data.addAll(position, list);
            notifyItemRangeInserted(position, list.size());

            if (data.size() > list.size()) {

                notifyItemChanged(list.size());
            }

            //nsNotifyItemRangeInserted(position, list.size());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
