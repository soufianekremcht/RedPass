package com.soufianekre.redpass.ui.passwords


import android.widget.SearchView
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.data.db.models.PasswordItem
import com.soufianekre.redpass.helpers.RxSearchObservable
import com.soufianekre.redpass.ui.base.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class PasswordListPresenter<V:PasswordListMvp.View>:BasePresenter<V>(),PasswordListMvp.Presenter<V>{



    override fun deletePassword(password : PasswordItem) {
        compositeDisposable.add(dataManager.getAppDatabase().passwordItemDoa().deletePasswordItem(password)
            .compose(schedulerProvider.ioToMainCompletableScheduler())
            .subscribe{
                getMvpView()?.onItemDeleted(password)
            }

        )
    }

    override fun getPasswords(query : String,label : Label?) {
        compositeDisposable.add(dataManager.getAppDatabase().passwordItemDoa().getPasswordList()
            .compose(schedulerProvider.ioToMainFlowableScheduler())
            .subscribe({
                var results : ArrayList<PasswordItem> = ArrayList()
                if (label == null){
                    for (passwordItem in  it){
                        if (passwordItem.title.contains(query)) results.add(passwordItem)
                    }
                    getMvpView()!!.notifyAdapter(results)
                }else{

                    for ( i in it.indices) {
                        if (it[i].label == label && it[i].title.contains(query)) results.add(it[i])

                    }
                    getMvpView()?.setToolbarTitle(label)
                    getMvpView()!!.notifyAdapter(results)
                }

            }, {
                getMvpView()!!.showMessage(it.localizedMessage!!)
            })



        )
    }



    override fun onItemClicked(passwordItem : PasswordItem,position:Int) {
        if (getMvpView()?.getActionMode() != null){
            getMvpView()?.toggleItemSelection(position)
        }else{
            getMvpView()?.openPasswordEditorActivity(passwordItem)
        }

    }

    override fun onItemLongClicked(position : Int) {
        if (getMvpView()!!.getActionMode() == null){
            getMvpView()!!.enableActionMode(position)
        }
    }

    override fun getSearchResults(searchView : SearchView) {
        compositeDisposable.add(RxSearchObservable.fromView(searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { text ->
                text.isNotEmpty()
            }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
               getPasswords(it,null)
            })
    }


}