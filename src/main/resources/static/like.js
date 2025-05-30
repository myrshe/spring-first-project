function toggleLike(postId, button) {
    fetch(`/like/${postId}`, {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            const likeIcon = button.querySelector('.like-icon img');
            const likeCountSpan = button.querySelector('.like-count');
            let likeCount = parseInt(likeCountSpan.textContent);

            const liked = button.getAttribute('data-liked') === 'true';

            if (liked) {
                // Был лайк — убираем
                likeIcon.src = "/images/empty-heart.png";
                button.setAttribute('data-liked', 'false');
                likeCountSpan.textContent = likeCount - 1;
            } else {
                // Не было лайка — ставим
                likeIcon.src = "/images/like2.png";
                button.setAttribute('data-liked', 'true');
                likeCountSpan.textContent = likeCount + 1;
            }
        }
    });
}
