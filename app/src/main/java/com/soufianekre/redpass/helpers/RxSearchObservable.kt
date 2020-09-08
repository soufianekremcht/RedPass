package com.soufianekre.redpass.helpers

import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


object RxSearchObservable {
    fun fromView(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()
        searchView.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                subject.onNext(newText!!);
                return true
            }

        })
        return subject
    }
}