const usersView = document.getElementById('usersView');
const coursesView = document.getElementById('coursesView');
const statusEl = document.getElementById('status');

function formBody(form) {
  return new URLSearchParams(new FormData(form));
}

async function refresh() {
  const [users, courses] = await Promise.all([
    fetch('/api/users').then(r => r.json()),
    fetch('/api/courses').then(r => r.json())
  ]);
  renderUsers(users);
  renderCourses(courses);
}

function setStatus(message, isError = false) {
  statusEl.textContent = message;
  statusEl.style.color = isError ? '#b91c1c' : '#166534';
}

function renderUsers(users) {
  if (!users.length) {
    usersView.innerHTML = '<p class="empty">No users available.</p>';
    return;
  }

  usersView.innerHTML = users.map((user) => `
    <article class="item-card">
      <div class="item-head">
        <h3>${escapeHtml(user.name)}</h3>
        <span class="badge">${escapeHtml(user.role)}</span>
      </div>
      <p><strong>ID:</strong> ${escapeHtml(user.id)}</p>
      <p><strong>Email:</strong> ${escapeHtml(user.email)}</p>
    </article>
  `).join('');
}

function renderCourses(courses) {
  if (!courses.length) {
    coursesView.innerHTML = '<p class="empty">No courses available.</p>';
    return;
  }

  coursesView.innerHTML = courses.map((course) => {
    const lessons = (course.lessons || [])
      .map((lesson) => `<li>${escapeHtml(lesson.title)} <span class="muted">(${escapeHtml(lesson.id)})</span></li>`)
      .join('');
    const students = (course.students || [])
      .map((student) => `<li>${escapeHtml(student.name)} <span class="muted">(${escapeHtml(student.id)})</span></li>`)
      .join('');

    return `
      <article class="item-card">
        <div class="item-head">
          <h3>${escapeHtml(course.title)}</h3>
          <span class="badge">${escapeHtml(course.id)}</span>
        </div>
        <p>${escapeHtml(course.description)}</p>
        <p><strong>Instructor:</strong> ${escapeHtml(course.instructorName || 'Unassigned')}</p>
        <div class="group">
          <p class="group-title">Lessons</p>
          <ul>${lessons || '<li class="muted">No lessons yet</li>'}</ul>
        </div>
        <div class="group">
          <p class="group-title">Enrolled Students</p>
          <ul>${students || '<li class="muted">No students enrolled</li>'}</ul>
        </div>
      </article>
    `;
  }).join('');
}

function escapeHtml(value) {
  return String(value ?? '')
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#39;');
}

async function post(url, body) {
  const response = await fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || 'Request failed');
  }
}

document.getElementById('userForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  try {
    await post('/api/users', formBody(e.target));
    e.target.reset();
    setStatus('User created successfully');
    await refresh();
  } catch (err) {
    setStatus(err.message, true);
  }
});

document.getElementById('courseForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  try {
    await post('/api/courses', formBody(e.target));
    e.target.reset();
    setStatus('Course created successfully');
    await refresh();
  } catch (err) {
    setStatus(err.message, true);
  }
});

document.getElementById('completeCourseForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  try {
    await post('/api/courses/complete', formBody(e.target));
    e.target.reset();
    setStatus('Complete course created successfully via Builder flow');
    await refresh();
  } catch (err) {
    setStatus(err.message, true);
  }
});

document.getElementById('assignForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);
  const courseId = formData.get('courseId');
  try {
    await post(`/api/courses/${courseId}/assign-instructor`, new URLSearchParams({ instructorId: formData.get('instructorId') }));
    e.target.reset();
    setStatus('Instructor assigned');
    await refresh();
  } catch (err) {
    setStatus(err.message, true);
  }
});

document.getElementById('enrollForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);
  const courseId = formData.get('courseId');
  try {
    await post(`/api/courses/${courseId}/enroll-student`, new URLSearchParams({ studentId: formData.get('studentId') }));
    e.target.reset();
    setStatus('Student enrolled');
    await refresh();
  } catch (err) {
    setStatus(err.message, true);
  }
});

refresh().catch((err) => setStatus(err.message, true));
