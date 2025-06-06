const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");


function toggleLike(postId, button) {
    fetch(`/like/${postId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        }
    }).then(response => {
        if (response.ok) {
            const likeIcon = button.querySelector('.like-icon img');
            const likeCountSpan = button.querySelector('.like-count');
            let likeCount = parseInt(likeCountSpan.textContent);

            const liked = button.getAttribute('data-liked') === 'true';

            if (liked) {
                // был лайк — убираем
                likeIcon.src = "/images/empty-heart.png";
                button.setAttribute('data-liked', 'false');
                likeCountSpan.textContent = likeCount - 1;
            } else {
                // не было лайка — ставим
                likeIcon.src = "/images/like2.png";
                button.setAttribute('data-liked', 'true');
                likeCountSpan.textContent = likeCount + 1;
            }
        }
    });
}

