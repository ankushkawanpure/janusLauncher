package com.blueberry.lab.januslauncher

import android.app.ListFragment
import android.app.LoaderManager
import android.content.Intent
import android.content.Loader
import android.os.Bundle
import android.view.View
import android.widget.ListView

/**
 * Created by jojo on 8/4/17.
 */

class AppListFragment : ListFragment(), LoaderManager.LoaderCallbacks<Array<AppModel>> {
    lateinit var mAdapter : AppListAdapter

    override fun onLoaderReset(p0: Loader<Array<AppModel>>?) {
        mAdapter.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setEmptyText("LOADING APPLICATIONS...")

        mAdapter = AppListAdapter(activity)

        listAdapter = mAdapter

        setListShown(false)

        loaderManager.initLoader(0, null, this)
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Array<AppModel>> {
        return AppsLoader(activity)
    }

    override fun onLoadFinished(loader: Loader<Array<AppModel>>?, apps: Array<AppModel>?) {
        mAdapter.setData(apps)

        if (isResumed) setListShown(true)
        else setListShownNoAnimation(true)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        val app = listAdapter.getItem(position) as? AppModel ?: return

        val intent = activity.packageManager.getLaunchIntentForPackage(app.packageName) as? Intent ?: return

        startActivity(intent)
    }
}