package com.bsrubacky.tapeeater.database

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.bsrubacky.tapeeater.database.entities.Media
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.util.Date

class MediaDatabaseTests : DatabaseTest() {
    @Test
    fun test_mediaInsert() {
        val media = Media(0, "Text", 1)
        Assert.assertEquals(" that Database is Empty", 0, tapeEaterDatabase.mediaDao().count())
        tapeEaterDatabase.mediaDao().insert(media)
        Assert.assertEquals(
            " that Database is no longer Empty",
            1,
            tapeEaterDatabase.mediaDao().count()
        )
    }

    @Test
    fun test_mediaSelect() {
        val media = Media(0, "Text", 1)
        media.id = tapeEaterDatabase.mediaDao().insert(media)
        Assert.assertEquals(
            " that Database is no longer Empty",
            1,
            tapeEaterDatabase.mediaDao().count()
        )
        val retrieved = tapeEaterDatabase.mediaDao().select(media.id)
        Assert.assertEquals(media, retrieved)
    }

    @Test
    fun test_mediaDelete() {
        val media = Media(0, "Text", 1)
        Assert.assertEquals(" that Database is Empty", 0, tapeEaterDatabase.mediaDao().count())
        media.id = tapeEaterDatabase.mediaDao().insert(media)
        Assert.assertEquals(
            " that Database is no longer Empty",
            1,
            tapeEaterDatabase.mediaDao().count()
        )
        tapeEaterDatabase.mediaDao().delete(media)
        Assert.assertEquals(
            " that Database is Empty Again",
            0,
            tapeEaterDatabase.mediaDao().count()
        )
    }

    @Test
    fun test_mediaSearchNoFiltersAlphaAsc() = runTest {
        val media1 = Media(0, "Wolfpack", 1)
        val media2 = Media(0, "Grief Counseling", 3)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchAlphaAscending("%%", "%")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[1])
        Assert.assertEquals(media2, retrieved.data[0])
    }

    @Test
    fun test_mediaSearchFilterAlphaAsc() = runTest {
        val media1 = Media(0, "Wolfpack", 1)
        val media2 = Media(0, "Grief Counseling", 3)
        val media3 = Media(0, "Furfag", 1)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        media3.id = tapeEaterDatabase.mediaDao().insert(media3)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchAlphaAscending("%%", "1")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[1])
        Assert.assertEquals(media3, retrieved.data[0])
    }

    @Test
    fun test_mediaSearchNoFiltersAlphaDesc() = runTest {
        val media1 = Media(0, "Wolfpack", 1)
        val media2 = Media(0, "Grief Counseling", 3)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchAlphaDescending("%%", "%")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[0])
        Assert.assertEquals(media2, retrieved.data[1])
    }

    @Test
    fun test_mediaSearchFilterAlphaDesc() = runTest {
        val media1 = Media(0, "Wolfpack", 1)
        val media2 = Media(0, "Grief Counseling", 3)
        val media3 = Media(0, "Furfag", 1)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        media3.id = tapeEaterDatabase.mediaDao().insert(media3)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchAlphaDescending("%%", "1")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[0])
        Assert.assertEquals(media3, retrieved.data[1])
    }

    @Test
    fun test_mediaSearchCase() = runTest {
        val media1 = Media(0, "Wolfpack", 1)
        val media2 = Media(0, "Grief Counseling", 3)
        val media3 = Media(0, "Furfag", 1)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        media3.id = tapeEaterDatabase.mediaDao().insert(media3)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchAlphaAscending("%wolf%", "%")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(1, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[0])
    }

    @Test
    fun test_mediaSearchFrag() = runTest {
        val media1 = Media(0, "Wolfpack", 1)
        val media2 = Media(0, "Grief Counseling", 3)
        val media3 = Media(0, "Furfag", 1)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        media3.id = tapeEaterDatabase.mediaDao().insert(media3)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchAlphaAscending("%f%", "%")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(3, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[2])
        Assert.assertEquals(media2, retrieved.data[1])
        Assert.assertEquals(media3, retrieved.data[0])
    }

    @Test
    fun test_mediaSearchNoFiltersDateAsc() = runTest {
        val media1 = Media(0, "Wolfpack", 1, lastTouched = Date(1758341937).time)
        val media2 = Media(0, "Grief Counseling", 3)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchDateAscending("%%", "%")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[0])
        Assert.assertEquals(media2, retrieved.data[1])
    }

    @Test
    fun test_mediaSearchFilterDateAsc() = runTest {
        val media1 = Media(0, "Wolfpack", 1, lastTouched = Date(1758341937).time)
        val media2 = Media(0, "Grief Counseling", 3)
        val media3 = Media(0, "Furfag", 1)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        media3.id = tapeEaterDatabase.mediaDao().insert(media3)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchDateAscending("%%", "1")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[0])
        Assert.assertEquals(media3, retrieved.data[1])
    }

    @Test
    fun test_mediaSearchNoFiltersDateDesc() = runTest {
        val media1 = Media(0, "Wolfpack", 1, lastTouched = Date(1758341937).time)
        val media2 = Media(0, "Grief Counseling", 3)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchDateDescending("%%", "%")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[1])
        Assert.assertEquals(media2, retrieved.data[0])
    }

    @Test
    fun test_mediaSearchFilterDateDesc() = runTest {
        val media1 = Media(0, "Wolfpack", 1, lastTouched = Date(1758341937).time)
        val media2 = Media(0, "Grief Counseling", 3)
        val media3 = Media(0, "Furfag", 1)
        media1.id = tapeEaterDatabase.mediaDao().insert(media1)
        media2.id = tapeEaterDatabase.mediaDao().insert(media2)
        media3.id = tapeEaterDatabase.mediaDao().insert(media3)
        val retrieved = TestPager(
            PagingConfig(20),
            tapeEaterDatabase.mediaDao().searchDateDescending("%%", "1")
        ).refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(2, retrieved.data.size)
        Assert.assertEquals(media1, retrieved.data[1])
        Assert.assertEquals(media3, retrieved.data[0])
    }

}
