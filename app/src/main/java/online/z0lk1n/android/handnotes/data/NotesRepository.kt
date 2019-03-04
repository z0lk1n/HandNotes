package online.z0lk1n.android.handnotes.data

import online.z0lk1n.android.handnotes.data.entity.Note

object NotesRepository {

    val notes: List<Note> = listOf(
        Note(
            "Первая заметка",
            "Текст первой заметки. Не очень длинный, но интересный"
        ),
        Note(
            "Вторая заметка",
            "Текст второй заметки. Не очень длинный, но интересный",
            0xff9575cd.toInt()
        ),
        Note(
            "Третья заметка",
            "Текст третьей заметки. Не очень длинный, но интересный",
            0xff64b5f6.toInt()
        ),
        Note(
            "Четвертая заметка",
            "Текст четвертой заметки. Не очень длинный, но интересный",
            0xff4db6ac.toInt()
        ),
        Note(
            "Пятая заметка",
            "Текст пятой заметки. Не очень длинный, но интересный",
            0xffb2ff59.toInt()
        ),
        Note(
            "Шестая заметка",
            "Текст шестой заметки. Не очень длинный, но интересный",
            0xffffeb3b.toInt()
        )
    )
}